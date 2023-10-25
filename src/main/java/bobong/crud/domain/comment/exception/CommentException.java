package bobong.crud.domain.comment.exception;

import lombok.Getter;

@Getter
public class CommentException extends RuntimeException{

    private final CommentErrorCode errorCode;
    private final String message;

    public CommentException(CommentErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
