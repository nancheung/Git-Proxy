package com.nancheung.gitproxy.api.git.common;

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
    private final String msg;
    
    /**
     * 数据体
     */
    @ApiModelProperty("数据体")
    private final T data;
    
    public static ApiResult<Void> success() {
        return build(GitProxyExceptionEnum.SUCCESS, null);
    }
    
    public static <T> ApiResult<T> success(T data) {
        return build(GitProxyExceptionEnum.SUCCESS, data);
    }
    
    public static ApiResult<Void> failed() {
        return build(GitProxyExceptionEnum.SYSTEM_ERROR, null);
    }
    
    public static ApiResult<Void> failed(String msg) {
        return build(GitProxyExceptionEnum.SYSTEM_ERROR.code(), msg, null);
    }
    
    public static <T> ApiResult<T> failed(T data) {
        return build(GitProxyExceptionEnum.SYSTEM_ERROR, data);
    }
    
    public static <T> ApiResult<T> failed(T data, String msg) {
        return build(GitProxyExceptionEnum.SYSTEM_ERROR.code(), msg, data);
    }
    
    public static ApiResult<Void> failed(GitProxyExceptionEnum exceptionEnum) {
        return build(exceptionEnum, null);
    }
    
    public static ApiResult<Void> failed(GitProxyExceptionEnum exceptionEnum, String msg) {
        return build(exceptionEnum.code(), msg, null);
    }
    
    public static <T> ApiResult<T> failed(GitProxyExceptionEnum exceptionEnum, T data) {
        return build(exceptionEnum, data);
    }
    
    private static <T> ApiResult<T> build(GitProxyExceptionEnum exceptionEnum, T data) {
        return build(exceptionEnum.code(), exceptionEnum.msg(), data);
    }
    
    private static <T> ApiResult<T> build(String code, String msg, T data) {
        return ApiResult.<T>builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }
}
