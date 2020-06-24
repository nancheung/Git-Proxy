package com.nancheung.gitproxy.common.restful.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web上下文工具类
 *
 * @author NanCheung
 */
@UtilityClass
public class WebContextUtils {
    public ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) getRequestAttributes();
    }
    
    public HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }
    
    public HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }
    
    private RequestAttributes getRequestAttributes() {
        return RequestContextHolder.getRequestAttributes();
    }
}
