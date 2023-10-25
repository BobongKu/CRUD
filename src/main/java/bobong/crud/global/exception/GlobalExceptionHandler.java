package bobong.crud.global.exception;

import bobong.crud.domain.comment.exception.CommentErrorResponse;
import bobong.crud.domain.comment.exception.CommentException;
import bobong.crud.domain.member.exception.MemberErrorResponse;
import bobong.crud.domain.member.exception.MemberException;
import bobong.crud.domain.post.exception.PostErrorResponse;
import bobong.crud.domain.post.exception.PostException;
import bobong.crud.global.file.exception.FileErrorResponse;
import bobong.crud.global.file.exception.FileException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //member exception
    @ExceptionHandler({MemberException.class})
    protected ResponseEntity memberException(MemberException e) {
        MemberErrorResponse response = new MemberErrorResponse(e.getErrorCode());
        return ResponseEntity.ok().body(response);
    }

    //post exception
    @ExceptionHandler({PostException.class})
    protected ResponseEntity postException(PostException e) {
        PostErrorResponse response = new PostErrorResponse(e.getErrorCode());
        return ResponseEntity.ok().body(response);
    }

    //file exception
    @ExceptionHandler({FileException.class})
    protected ResponseEntity fileException(FileException e) {
        FileErrorResponse response = new FileErrorResponse(e.getErrorCode());
        return ResponseEntity.ok().body(response);
    }

    //comment exception
    @ExceptionHandler({CommentException.class})
    protected ResponseEntity commentException(CommentException e) {
        CommentErrorResponse response = new CommentErrorResponse(e.getErrorCode());
        return ResponseEntity.ok().body(response);
    }
}
