package com.nancheung.gitproxy.common.restful;

import org.springframework.http.HttpStatus;

/**
 * 实现Restful风格的HTTP状态码接口
 *
 * @author NanCheung
 */
public interface RestfulHttpStatus {
    /**
     * HTTP状态码
     */
    HttpStatus httpStatus();
}
