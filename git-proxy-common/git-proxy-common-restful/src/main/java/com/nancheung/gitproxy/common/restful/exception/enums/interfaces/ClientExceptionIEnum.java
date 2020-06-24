package com.nancheung.gitproxy.common.restful.exception.enums.interfaces;

import com.nancheung.gitproxy.common.restful.RestfulHttpStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 客户端异常枚举接口
 *
 * @author NanCheung
 */
public interface ClientExceptionIEnum extends GitProxyExceptionIEnum, RestfulHttpStatus {
    
    ClientExceptionIEnum CLIENT_ERROR = DefaultClientExceptionEnum.CLIENT_ERROR;
    
    String SOURCE = "A";
    
    @Override
    default String source() {
        return SOURCE;
    }
    
    
    @AllArgsConstructor
    enum DefaultClientExceptionEnum implements ClientExceptionIEnum {
        CLIENT_ERROR("0001", "客户端错误");
        
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
}
