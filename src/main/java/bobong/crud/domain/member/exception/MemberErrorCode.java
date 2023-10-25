package bobong.crud.domain.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {

    MEMBER_NOT_FOUND(201,"유저를 찾을 수 없습니다."),
    DUPLICATE_MEMBER_USERNAME(201,"이미 존재하는 아이디입니다.");

    private final int status;
    private final String message;
}
