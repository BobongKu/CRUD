package bobong.crud.domain.member.controller;

import bobong.crud.domain.member.dto.MemberInfoDto;
import bobong.crud.domain.member.dto.MemberSingUpDto;
import bobong.crud.domain.member.dto.MemberUpdateDto;
import bobong.crud.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor //자동 생성자 주입
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;

    //가입
    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    public void singUp(@Valid @RequestBody MemberSingUpDto memberSingUpDto) throws Exception{
        memberService.singUp(memberSingUpDto);
    }

    //유저 조회
    @GetMapping("/member/{id}")
    public ResponseEntity getinfo(@Valid @PathVariable("id") Long id) throws Exception{
        MemberInfoDto info = memberService.getInfo(id);
        return new ResponseEntity(info, HttpStatus.OK);
    }

    //내정보 조회
    @GetMapping("/member")
    public ResponseEntity getMyInfo(HttpServletResponse response) throws Exception{
        MemberInfoDto info = memberService.getMyInfo();
        return new ResponseEntity(info,HttpStatus.OK);
    }

    //회원정보 수정
    @PutMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    public void updateBasicInfo(@Valid @RequestBody MemberUpdateDto memberUpdateDto) throws Exception{
        memberService.update(memberUpdateDto);
    }
}
