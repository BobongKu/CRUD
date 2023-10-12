package bobong.crud.domain.post.exception;

import bobong.crud.global.exception.BaseException;
import bobong.crud.global.exception.BaseExceptionType;

public class PostException extends BaseException {


    private BaseExceptionType baseExceptionType;

    public PostException(BaseExceptionType baseExceptionType){
        this.baseExceptionType = baseExceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return this.baseExceptionType;
    }
}
