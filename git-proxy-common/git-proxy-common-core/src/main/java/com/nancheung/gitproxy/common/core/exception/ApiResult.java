package com.nancheung.gitproxy.common.core.exception;

import com.nancheung.gitproxy.common.core.exception.enums.GitProxyExceptionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Api返回结果
 *
 * @author NanCheung
 */
@ToString
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 错误码
     */
    @ApiModelProperty("错误码")
    private final String code;
    
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private final String message;
    
    /**
     * 异常信息
     */
    @ApiModelProperty("异常信息")
    private final String errorCause;
    
    /**
     * 数据体
     */
    @ApiModelProperty("数据体")
    private final T data;
    
    public static ApiResult<Void> success() {
        return success(null);
    }
    
    public static <T> ApiResult<T> success(T data) {
        return build(GitProxyExceptionEnum.SUCCESS, null, data);
    }
    
    public static ApiResult<Void> failed() {
        return failed(GitProxyExceptionEnum.SYSTEM_ERROR);
    }
    
    public static ApiResult<Void> failed(GitProxyExceptionEnum exceptionEnum) {
        return failed(exceptionEnum, null);
    }
    
    public static ApiResult<Void> failed(GitProxyExceptionEnum exceptionEnum, String errorCause) {
        return build(exceptionEnum, errorCause, null);
    }
    
    private static <T> ApiResult<T> build(GitProxyExceptionEnum exceptionEnum, String errorCause, T data) {
        return build(exceptionEnum.code(), exceptionEnum.message(), errorCause, data);
    }
    
    private static <T> ApiResult<T> build(String code, String message, String errorCause, T data) {
        return ApiResult.<T>builder()
                .code(code)
                .message(message)
                .errorCause(errorCause)
                .data(data)
                .build();
    }
}
