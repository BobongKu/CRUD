package bobong.crud.domain.member.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException{

    private MemberErrorCode errorCode;
    private String message;

    public MemberException(MemberErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
