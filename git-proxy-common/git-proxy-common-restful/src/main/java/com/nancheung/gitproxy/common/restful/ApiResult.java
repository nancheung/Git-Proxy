package com.nancheung.gitproxy.common.restful;

import com.nancheung.gitproxy.common.restful.exception.enums.interfaces.GitProxyExceptionIEnum;
import com.nancheung.gitproxy.common.restful.exception.enums.interfaces.SystemExceptionIEnum;
import com.nancheung.gitproxy.common.restful.util.WebContextUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.servlet.http.HttpServletResponse;
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
        return build(GitProxyExceptionIEnum.SUCCESS, null, data);
    }
    
    public static ApiResult<Void> failed() {
        return failed(SystemExceptionIEnum.SYSTEM_ERROR);
    }
    
    public static ApiResult<Void> failed(GitProxyExceptionIEnum exceptionEnum) {
        return failed(exceptionEnum, null);
    }
    
    public static ApiResult<Void> failed(GitProxyExceptionIEnum exceptionEnum, String errorCause) {
        return build(exceptionEnum, errorCause, null);
    }
    
    private static <T> ApiResult<T> build(GitProxyExceptionIEnum exceptionEnum, String errorCause, T data) {
        // 设置异常枚举中的自定义http状态码
        if (exceptionEnum instanceof RestfulHttpStatus) {
            HttpServletResponse response = WebContextUtils.getResponse();
            response.setStatus(((RestfulHttpStatus) exceptionEnum).httpStatus().value());
        }
        
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
