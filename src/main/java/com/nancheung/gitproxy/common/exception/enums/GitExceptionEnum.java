package com.nancheung.gitproxy.common.exception.enums;

import lombok.AllArgsConstructor;

/**
 * Git异常枚举
 *
 * @author NanCheung
 */
@AllArgsConstructor
public enum GitExceptionEnum implements GitProxyExceptionEnum {
    GIT_ERROR("D0001", "Git操作失败"),
    GIT_DOWNLOAD_THREAD_POOL_FULL("D0100", "Git下载线程池满");
    
    private String code;
    private String msg;
    
    @Override
    public String code() {
        return code;
    }
    
    @Override
    public String msg() {
        return msg;
    }
}
