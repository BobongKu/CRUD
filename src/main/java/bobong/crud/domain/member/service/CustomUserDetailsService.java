package bobong.crud.domain.member.service;

import bobong.crud.domain.member.entity.Member;
import bobong.crud.domain.member.exception.MemberErrorCode;
import bobong.crud.domain.member.exception.MemberException;
import bobong.crud.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return User.builder().username(member.getUsername()).password(member.getPassword()).roles(member.getRole().name()).build(); //등록
    }
}
