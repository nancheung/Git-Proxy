package com.nancheung.gitproxy.common.restful.exception.enums.interfaces;

import lombok.AllArgsConstructor;

/**
 * 异常枚举接口
 * 所有异常枚举应当实现该接口
 * <p>依据阿里巴巴Java开发手册（泰山版）制定</p>
 *
 * @author NanCheung
 * @see <a href="https://github.com/alibaba/p3c">阿里巴巴Java开发手册</a>
 */
public interface GitProxyExceptionIEnum {
    /**
     * 一切OK
     */
    GitProxyExceptionIEnum SUCCESS = DefaultExceptionIEnum.SUCCESS;
    
    /**
     * 来源
     */
    String source();
    
    /**
     * 编号
     */
    String number();
    
    /**
     * 描述
     */
    String message();
    
    /**
     * 错误码
     */
    default String code() {
        return this.source() + this.number();
    }
    
    
    @AllArgsConstructor
    enum DefaultExceptionIEnum implements GitProxyExceptionIEnum {
        SUCCESS("0000", "成功");
        
        public static final String SOURCE = "0";
        
        /**
         * 错误码
         */
        private final String number;
        /**
         * 描述
         */
        private final String message;
        
        @Override
        public String source() {
            return SOURCE;
        }
        
        @Override
        public String number() {
            return number;
        }
        
        @Override
        public String message() {
            return message;
        }
    }
}
