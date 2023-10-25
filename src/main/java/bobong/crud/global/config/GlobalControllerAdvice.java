package bobong.crud.global.config;

import bobong.crud.global.util.LogUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalControllerAdvice {

    private final HttpServletRequest request;

    @ModelAttribute
    public void logging(){
        LogUtil.logInfo(request.getRequestURI(), request.getMethod());
    }
}
