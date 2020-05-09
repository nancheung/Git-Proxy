package com.nancheung.gitproxy.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Api返回结果
 *
 * @author NanCheung
 */
@ToString
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 错误码
     * <p>依据阿里巴巴Java开发手册（泰山版）制定</p>
     *
     * @see <a href="https://github.com/alibaba/p3c">阿里巴巴Java开发手册</a>
     */
    private final String code;
    
    /**
     * 描述
     */
    private final String msg;
    
    /**
     * 数据体
     */
    private final T data;
    
    public static ApiResult<Void> success() {
        return build("00000", null, null);
    }
    
    public static <T> ApiResult<T> success(T data) {
        return build("00000", null, data);
    }
    
    public static ApiResult<Void> failed() {
        return build("B0001", null, null);
    }
    
    public static ApiResult<Void> failed(String msg) {
        return build("B0001", msg, null);
    }
    
    public static <T> ApiResult<T> failed(T data) {
        return build("B0001", null, data);
    }
    
    public static <T> ApiResult<T> failed(T data, String msg) {
        return build("B0001", msg, data);
    }
    
    private static <T> ApiResult<T> build(String code, String msg, T data) {
        return ApiResult.<T>builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }
}
