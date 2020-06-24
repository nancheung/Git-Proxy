package com.nancheung.gitproxy.common.restful.exception.enums.interfaces;

import com.nancheung.gitproxy.common.restful.RestfulHttpStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 第三方异常枚举接口
 *
 * @author NanCheung
 */
public interface ThirdPartyExceptionIEnum extends GitProxyExceptionIEnum, RestfulHttpStatus {
    
    ThirdPartyExceptionIEnum THIRD_PARTY_ERROR = ThirdPartyExceptionIEnum.DefaultThirdPartyExceptionEnum.THIRD_PARTY_ERROR;
    
    String SOURCE = "C";
    
    @Override
    default String source() {
        return SOURCE;
    }
    
    
    @AllArgsConstructor
    enum DefaultThirdPartyExceptionEnum implements ThirdPartyExceptionIEnum {
        THIRD_PARTY_ERROR("0001", "第三方服务错误");
        
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
