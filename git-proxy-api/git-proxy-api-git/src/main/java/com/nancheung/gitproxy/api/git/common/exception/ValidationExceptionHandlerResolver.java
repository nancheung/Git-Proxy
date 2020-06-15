package com.nancheung.gitproxy.api.git.common.exception;

import com.nancheung.gitproxy.api.git.common.ApiResult;
import com.nancheung.gitproxy.common.core.exception.enums.ClientExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 参数校验异常处理解析器
 *
 * @author NanCheung
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ValidationExceptionHandlerResolver {
    /**
     * 参数校验失败
     *
     * @param e 违规参数异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResult<Void> handleConstraintViolationException(ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .findFirst()
                .map(constraintViolation -> ApiResult.failed(ClientExceptionEnum.REQUEST_PARAMETER_ERROR, constraintViolation.getMessage()))
                .orElseGet(ApiResult::failed);
    }
    
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return e.getBindingResult().getAllErrors()
                .stream()
                .findFirst()
                .map(objectError -> ApiResult.failed(ClientExceptionEnum.REQUEST_PARAMETER_ERROR, objectError.getDefaultMessage()))
                .orElseGet(ApiResult::failed);
    }
    
    @ExceptionHandler(BindException.class)
    public ApiResult<Void> handleBindException(BindException e) {
        return e.getBindingResult().getAllErrors()
                .stream()
                .findFirst()
                .map(objectError -> ApiResult.failed(ClientExceptionEnum.REQUEST_PARAMETER_ERROR, objectError.getDefaultMessage()))
                .orElseGet(ApiResult::failed);
    }
    
}
