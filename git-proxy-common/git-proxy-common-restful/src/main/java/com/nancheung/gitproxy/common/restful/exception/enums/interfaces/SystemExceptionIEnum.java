package com.nancheung.gitproxy.common.restful.exception.enums.interfaces;

import com.nancheung.gitproxy.common.restful.RestfulHttpStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 系统异常枚举接口
 *
 * @author NanCheung
 */

public interface SystemExceptionIEnum extends GitProxyExceptionIEnum, RestfulHttpStatus {
    
    SystemExceptionIEnum SYSTEM_ERROR = SystemExceptionIEnum.DefaultSystemExceptionEnum.SYSTEM_ERROR;
    
    String SOURCE = "B";
    
    @Override
    default String source() {
        return SOURCE;
    }
    
    
    @AllArgsConstructor
    enum DefaultSystemExceptionEnum implements SystemExceptionIEnum {
        SYSTEM_ERROR("0001", "系统错误");
        
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
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
