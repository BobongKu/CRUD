package bobong.crud.domain.member.controller;

import bobong.crud.domain.member.dto.MemberLoginDto;
import bobong.crud.domain.member.dto.TokenDto;
import bobong.crud.global.jwt.filter.JwtFilter;
import bobong.crud.global.jwt.service.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody MemberLoginDto memberLoginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberLoginDto.getUsername(), memberLoginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //토큰 생성
        String jwt = jwtTokenProvider.createToken(authentication);

        //response Header 토큰추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

}
