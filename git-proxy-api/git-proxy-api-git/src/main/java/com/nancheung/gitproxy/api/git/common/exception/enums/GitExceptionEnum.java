package com.nancheung.gitproxy.api.git.common.exception.enums;

import com.nancheung.gitproxy.common.restful.exception.enums.interfaces.ClientExceptionIEnum;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Git异常枚举
 *
 * @author NanCheung
 */
@AllArgsConstructor
public enum GitExceptionEnum implements ClientExceptionIEnum {
    GIT_ERROR("0200", "Git操作失败", HttpStatus.BAD_REQUEST),
    GIT_DOWNLOAD_THREAD_POOL_FULL("0201", "Git下载线程池满", HttpStatus.TOO_MANY_REQUESTS);
    
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
