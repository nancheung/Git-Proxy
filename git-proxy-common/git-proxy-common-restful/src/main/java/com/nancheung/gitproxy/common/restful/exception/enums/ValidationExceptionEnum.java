package com.nancheung.gitproxy.common.restful.exception.enums;

import com.nancheung.gitproxy.common.restful.RestfulHttpStatus;
import com.nancheung.gitproxy.common.restful.exception.enums.interfaces.ClientExceptionIEnum;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 校验异常枚举
 *
 * @author NanCheung
 */
@AllArgsConstructor
public enum ValidationExceptionEnum implements ClientExceptionIEnum, RestfulHttpStatus {
    REQUEST_PARAMETER_ERROR("0100", "用户请求参数错误"),
    USER_INPUT_IS_ILLEGAL("0101", "用户输入内容非法");
    
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
        return HttpStatus.BAD_REQUEST;
    }
}
