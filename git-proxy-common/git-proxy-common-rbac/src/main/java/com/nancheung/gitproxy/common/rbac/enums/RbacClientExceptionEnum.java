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
    AUTHENTICATION_FAILED("0002", "授权失败");
    
    private final String number;
    private final String message;
    
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
        return HttpStatus.UNAUTHORIZED;
    }
}