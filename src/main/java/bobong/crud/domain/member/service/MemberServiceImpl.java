package bobong.crud.domain.member.service;

import bobong.crud.domain.member.dto.MemberInfoDto;
import bobong.crud.domain.member.dto.MemberSingUpDto;
import bobong.crud.domain.member.dto.MemberUpdateDto;
import bobong.crud.domain.member.entity.Member;
import bobong.crud.domain.member.exception.MemberErrorCode;
import bobong.crud.domain.member.exception.MemberException;
import bobong.crud.domain.member.repository.MemberRepository;
import bobong.crud.global.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    //회원가입
    @Override
    public void singUp(MemberSingUpDto memberSingUpDto) throws Exception {
        Member member = memberSingUpDto.toEntity();

        //권한 자동 부여
        member.addUserAuthority();

        //입력받은 비밀번호 암호화
        member.encodedPassword(passwordEncoder);

        //중복 조회
        if(memberRepository.findByUsername(memberSingUpDto.username()).isPresent()){
            throw new MemberException(MemberErrorCode.DUPLICATE_MEMBER_USERNAME);
        }

        memberRepository.save(member);
    }

    //회원정보 조회
    @Override
    public MemberInfoDto getInfo(Long id) throws Exception {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return new MemberInfoDto(findMember);
    }

    //내정보 조회
    @Override
    public MemberInfoDto getMyInfo() throws Exception {
        Member findMember = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return new MemberInfoDto(findMember);
    }


    //회원정보 수정
    @Override
    public void update(MemberUpdateDto memberUpdateDto) throws Exception {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        memberUpdateDto.nickName().ifPresent(member::updateNickName);
    }
}
