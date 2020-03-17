package vc.thinker.config.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import vc.thinker.common.response.AbstractResponse;
import vc.thinker.common.response.PageResponse;
import vc.thinker.common.response.SimpleResponse;
import vc.thinker.common.response.SingleResponse;

import java.lang.annotation.Annotation;

/**
 * 统一结果返回处理
 *
 * @author HeTongHao
 * @since 2020-01-02 11:49
 */
@RestControllerAdvice(annotations = RestController.class)
public class UniteResponseAdvice implements ResponseBodyAdvice<Object> {
    private static final String VOID = "void";

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        for (Annotation typeAnnotation : methodParameter.getDeclaringClass().getAnnotations()) {
            if (typeAnnotation instanceof InconsistentReturn) {
                return false;
            }
        }
        for (Annotation methodAnnotation : methodParameter.getMethodAnnotations()) {
            if (methodAnnotation instanceof InconsistentReturn) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object result, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (VOID.equals(methodParameter.getParameterType().getName())) {
            return new SimpleResponse();
        } else if (result instanceof AbstractResponse) {
            return result;
        } else if (result instanceof IPage) {
            return new PageResponse<>().setPage((IPage) result);
        } else {
            SingleResponse<Object> response = new SingleResponse<>();
            response.setData(result);
            return response;
        }
    }
}
