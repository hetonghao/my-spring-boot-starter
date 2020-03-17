package vc.thinker.config.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vc.thinker.common.biz.exception.AbstractServiceException;
import vc.thinker.common.response.AbstractResponse;
import vc.thinker.common.response.SimpleResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一结果返回处理
 *
 * @author HeTongHao
 * @since 2020-01-02 11:49
 */
@RestControllerAdvice(annotations = RestController.class)
public class UniteResponseThrowHandler {

    /**
     * 通用业务异常消息处理
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(AbstractServiceException.class)
    public AbstractResponse serviceException(AbstractServiceException e) {
        AbstractResponse errorResponse = new SimpleResponse();
        errorResponse.setErrorInfo(e.getCode(), e.getMessage());
        return errorResponse;
    }

    /**
     * 参数错误处理
     *
     * @param req
     * @param resp
     * @param ex
     * @throws IOException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgumentException(HttpServletRequest req, HttpServletResponse resp, IllegalArgumentException ex)
            throws IOException {
        resp.sendError(HttpStatus.BAD_REQUEST.value(), "参数错误:" + ex.getMessage());
    }
}
