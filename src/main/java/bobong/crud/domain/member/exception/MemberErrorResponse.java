package bobong.crud.domain.member.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberErrorResponse {

    private int status;
    private String message;

    public MemberErrorResponse(MemberErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }
}
