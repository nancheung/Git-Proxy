package com.nancheung.gitproxy.common.core.exception.enums;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * 异常枚举接口
 * 所有异常枚举应当实现该接口
 * <p>依据阿里巴巴Java开发手册（泰山版）制定</p>
 *
 * @author NanCheung
 * @see <a href="https://github.com/alibaba/p3c">阿里巴巴Java开发手册</a>
 */
public interface GitProxyExceptionEnum {
    /**
     * 一切OK
     */
    GitProxyExceptionEnum SUCCESS = DefaultExceptionEnum.SUCCESS;
    /**
     * 系统执行出错
     */
    GitProxyExceptionEnum SYSTEM_ERROR = DefaultExceptionEnum.SYSTEM_ERROR;
    
    /**
     * 获取错误码
     *
     * @return 错误码
     */
    String code();
    
    /**
     * 获取描述
     *
     * @return 描述
     */
    String msg();
    
    @ToString
    @AllArgsConstructor
    enum DefaultExceptionEnum implements GitProxyExceptionEnum {
        SUCCESS("00000", "成功"),
        SYSTEM_ERROR("B0001", "系统执行出错");
        
        /**
         * 错误码
         */
        private final String code;
        /**
         * 描述
         */
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
}
