package com.nancheung.gitproxy.task;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.nancheung.gitproxy.GitProxyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务自动配置类
 *
 * @author NanCheung
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(CleanStrategyProperties.class)
public class TaskAutoConfiguration {
    
    /**
     * 初始化任务
     *
     * @param gitProxyProperties 系统配置
     * @return 初始化执行器
     */
    @Bean
    public InitCommandLineRunner initDirCommandLineRunner(GitProxyProperties gitProxyProperties) {
        return new InitCommandLineRunner(gitProxyProperties);
    }
    
    /**
     * 清理文件任务
     *
     * @param gitProxyProperties 系统配置
     * @return 清理文件执行器
     */
    @Bean
    public CleanFileCommandLineRunner cleanCommandLineRunner(GitProxyProperties gitProxyProperties, CleanStrategyProperties cleanStrategyProperties) {
        return new CleanFileCommandLineRunner(gitProxyProperties, cleanStrategyProperties);
    }
    
    /**
     * git clone的线程池
     *
     * @return 线程池
     */
    @Bean
    public Executor gitCloneExecutor() {
        return new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1),
                ThreadFactoryBuilder.create().setNamePrefix("git-clone-pool-").build(), new ThreadPoolExecutor.AbortPolicy());
    }
    
}
