package com.nancheung.gitproxy.common;

import com.nancheung.gitproxy.common.exception.GitProxyRejectedExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理解析器
 *
 * @author NanCheung
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerResolver {
    
    /**
     * 线程池满，不能提交任务
     *
     * @param e {@link GitProxyRejectedExecutionException}
     */
    @ExceptionHandler(GitProxyRejectedExecutionException.class)
    public ApiResult<Void> handleException(GitProxyRejectedExecutionException e) {
        return ApiResult.failed(e.getLocalizedMessage());
    }
    
    /**
     * 线程池满，不能提交任务
     * <p>
     * 使用Spring的 {@link Async} 注解时,真正的异常会被封装为 {@link TaskRejectedException}
     * </p>
     *
     * @param e {@link TaskRejectedException}
     */
    @ExceptionHandler(TaskRejectedException.class)
    public ApiResult<Void> handleException(TaskRejectedException e) {
        return ApiResult.failed(e.getCause().getLocalizedMessage());
    }
    
    /**
     * 未被特殊处理的其他异常
     *
     * @param e {@link Exception}
     */
    @ExceptionHandler(Exception.class)
    public ApiResult<Void> handleException(Exception e) {
        log.error("未捕获的异常", e);
        return ApiResult.failed(e.getLocalizedMessage());
    }
}
