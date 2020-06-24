package com.nancheung.gitproxy.common.restful.exception;

import com.nancheung.gitproxy.common.restful.ApiResult;
import com.nancheung.gitproxy.common.restful.exception.enums.interfaces.SystemExceptionIEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 默认异常处理解析器
 *
 * @author NanCheung
 */
@Slf4j
@Order
@RestControllerAdvice
public class DefaultExceptionHandlerResolver {
    /**
     * 默认的异常处理
     *
     * @param e {@link Exception}
     */
    @ExceptionHandler(Exception.class)
    public ApiResult<Void> handleException(Exception e) {
        log.error("未捕获的异常", e);
        return ApiResult.failed(SystemExceptionIEnum.SYSTEM_ERROR, e.getLocalizedMessage());
    }
}
