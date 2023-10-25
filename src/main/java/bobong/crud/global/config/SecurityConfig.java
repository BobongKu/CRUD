package bobong.crud.global.config;

import bobong.crud.global.jwt.handler.JwtAccessDeniedHandler;
import bobong.crud.global.jwt.handler.JwtAuthenticationEntryPoint;
import bobong.crud.global.jwt.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig{

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    //Http Config
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //api 특성상 csrf 비활성화
        http.csrf(AbstractHttpConfigurer::disable);
        //api 특성상 formLogin 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);
        //JWT토큰 사용으로 session 비활성화
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //로그인, 회원가입, docs를 제외한 모든 사이트는 인증받아야만 request 가능
        http.authorizeHttpRequests(request -> request
                .requestMatchers(HttpMethod.POST, "/auth/signUp","/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/post/{postId}","/post", "/member/{id}").permitAll()
                .anyRequest().authenticated());//권한 수정 필요
        //에러 처리
        http.exceptionHandling(authenticationManager -> authenticationManager
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler));
        //JwtSecurityConfig 적용
        http.apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
    }

    //swagger-ui 사용을 위한 ignoring 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web ->   web.ignoring().requestMatchers(
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/api-docs/**");
    }

    //PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
