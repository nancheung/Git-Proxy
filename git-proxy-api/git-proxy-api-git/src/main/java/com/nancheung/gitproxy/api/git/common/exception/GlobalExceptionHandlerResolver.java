package com.nancheung.gitproxy.api.git.common.exception;

import com.nancheung.gitproxy.api.git.common.exception.enums.GitExceptionEnum;
import com.nancheung.gitproxy.common.restful.ApiResult;
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
     */
    @ExceptionHandler(GitProxyRejectedExecutionException.class)
    public ApiResult<Void> handleGitProxyRejectedExecutionException() {
        return ApiResult.failed(GitExceptionEnum.GIT_DOWNLOAD_THREAD_POOL_FULL);
    }
    
    /**
     * 线程池满，不能提交任务
     * <p>
     * 使用Spring的 {@link Async} 注解时,真正的异常会被封装为 {@link TaskRejectedException}
     * </p>
     */
    @ExceptionHandler(TaskRejectedException.class)
    public ApiResult<Void> handleTaskRejectedException() {
        return ApiResult.failed(GitExceptionEnum.GIT_DOWNLOAD_THREAD_POOL_FULL);
    }
}
