package com.nancheung.gitproxy.common.rbac.enums;

import com.nancheung.gitproxy.common.restful.exception.enums.interfaces.ClientExceptionIEnum;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 鉴权失败错误枚举
 *
 * @author NanCheung
 */
@AllArgsConstructor
public enum RbacClientExceptionEnum implements ClientExceptionIEnum {
    /**
     * 未经授权
     */
    UNAUTHORIZED("0300", "未经授权", HttpStatus.UNAUTHORIZED),
    /**
     * 授权失败
     */
    AUTHORIZED_FAILED("0301", "授权失败", HttpStatus.FORBIDDEN);
    
    private final String number;
    private final String message;
    private final HttpStatus httpStatus;
    
    @Override
    public String number() {
        return number;
    }
    
    @Override
    public String message() {
        return message;
    }
    
    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }
}