package com.nancheung.gitproxy.common.restful.exception;

/**
 * 统一异常类
 *
 * @author NanCheung
 */
public class GitProxyException extends RuntimeException {
    public GitProxyException() {
        super();
    }
    
    public GitProxyException(String message) {
        super(message);
    }
    
    public GitProxyException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public GitProxyException(Throwable cause) {
        super(cause);
    }
    
    protected GitProxyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
