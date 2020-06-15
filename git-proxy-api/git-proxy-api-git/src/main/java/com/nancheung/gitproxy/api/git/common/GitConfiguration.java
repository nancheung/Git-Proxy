package com.nancheung.gitproxy.api.git.common;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.nancheung.gitproxy.api.git.common.exception.GitCloneRejectedExecutionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * Git配置类
 *
 * @author NanCheung
 */
@Configuration
public class GitConfiguration {
    
    /**
     * git clone的线程池
     *
     * @return 线程池
     */
    @Bean
    public Executor gitCloneExecutor() {
        RejectedExecutionHandler rejectedExecutionHandler = (r, executor) -> {
            throw new GitCloneRejectedExecutionException(r, executor);
        };
        return new ThreadPoolExecutor(1, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2),
                ThreadFactoryBuilder.create().setNamePrefix("git-clone-pool-").build(), rejectedExecutionHandler);
    }
}
