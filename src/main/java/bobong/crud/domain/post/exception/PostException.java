package bobong.crud.domain.post.exception;

import lombok.Getter;

@Getter
public class PostException extends RuntimeException{

    private final PostErrorCode errorCode;
    private final String message;

    public PostException(PostErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
