package bobong.crud.global.file.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileErrorResponse {

    private int status;
    private String message;

    public FileErrorResponse(FileErrorCode postErrorCode) {
        this.status = postErrorCode.getStatus();
        this.message = postErrorCode.getMessage();
    }
}
