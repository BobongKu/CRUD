package bobong.crud.domain.member.service;

import bobong.crud.domain.member.dto.MemberInfoDto;
import bobong.crud.domain.member.dto.MemberSingUpDto;
import bobong.crud.domain.member.dto.MemberUpdateDto;

public interface MemberService {

    void singUp (MemberSingUpDto memberSingUpDto) throws Exception;

    void update(MemberUpdateDto memberUpdateDto) throws Exception;

    MemberInfoDto getInfo(Long id) throws Exception;

    MemberInfoDto getMyInfo() throws Exception;
}
