package com.nancheung.gitproxy.api.git.common.exception;

import com.nancheung.gitproxy.common.restful.ApiResult;
import com.nancheung.gitproxy.common.restful.exception.enums.ValidationExceptionEnum;
import lombok.extern.slf4j.Slf4j;
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
                .map(constraintViolation -> ApiResult.failed(ValidationExceptionEnum.USER_INPUT_IS_ILLEGAL, constraintViolation.getMessage()))
                .orElseGet(ApiResult::failed);
    }
    
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return e.getBindingResult().getAllErrors()
                .stream()
                .findFirst()
                .map(objectError -> ApiResult.failed(ValidationExceptionEnum.USER_INPUT_IS_ILLEGAL, objectError.getDefaultMessage()))
                .orElseGet(ApiResult::failed);
    }
    
    @ExceptionHandler(BindException.class)
    public ApiResult<Void> handleBindException(BindException e) {
        return e.getBindingResult().getAllErrors()
                .stream()
                .findFirst()
                .map(objectError -> ApiResult.failed(ValidationExceptionEnum.USER_INPUT_IS_ILLEGAL, objectError.getDefaultMessage()))
                .orElseGet(ApiResult::failed);
    }
    
}
