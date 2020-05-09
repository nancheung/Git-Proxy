package com.nancheung.gitproxy.git.exception;

import com.nancheung.gitproxy.common.exception.GitProxyRejectedExecutionException;
import lombok.Getter;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Git Clone 拒绝执行任务异常
 *
 * @author NanCheung
 */
@Getter
public class GitCloneRejectedExecutionException extends GitProxyRejectedExecutionException {
    
    /**
     * 提交的任务
     */
    private final Runnable runnable;
    
    /**
     * 被提交的线程池
     */
    private final ThreadPoolExecutor threadPoolExecutor;
    
    
    public GitCloneRejectedExecutionException(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        this.runnable = runnable;
        this.threadPoolExecutor = threadPoolExecutor;
    }
    
    @Override
    public String getLocalizedMessage() {
        return "下载队列已满！最大同时下载数：" + threadPoolExecutor.getPoolSize() + "，正在下载：" + threadPoolExecutor.getActiveCount() + "，正在等待：" + threadPoolExecutor.getQueue().size() + "，已完成：" + threadPoolExecutor.getCompletedTaskCount();
    }
}
