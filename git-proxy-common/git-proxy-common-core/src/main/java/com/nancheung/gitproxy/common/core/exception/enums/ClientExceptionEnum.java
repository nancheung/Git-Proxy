package com.nancheung.gitproxy.common.core.exception.enums;

import lombok.AllArgsConstructor;

/**
 * 客户端异常枚举
 *
 * @author NanCheung
 */
@AllArgsConstructor
public enum ClientExceptionEnum implements GitProxyExceptionEnum {
    CLIENT_ERROR("A0001", "客户端错误"),
    REQUEST_PARAMETER_ERROR("A0400", "用户请求参数错误");
    
    private final String code;
    private final String msg;
    
    @Override
    public String code() {
        return code;
    }
    
    @Override
    public String msg() {
        return msg;
    }
}
