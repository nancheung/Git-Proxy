package com.nancheung.gitproxy.task;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.nancheung.gitproxy.GitProxyProperties;
import com.nancheung.gitproxy.task.runner.CleanTempDirRunnable;
import com.nancheung.gitproxy.task.runner.CleanZipFileRunnable;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 清理文件执行器
 *
 * @author NanCheung
 */
@AllArgsConstructor
public class CleanFileCommandLineRunner implements CommandLineRunner {
    
    private final GitProxyProperties gitProxyProperties;
    
    @Override
    public void run(String... args) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new ThreadFactoryBuilder().setNamePrefix("clean-pool-%d").build());
        executorService.scheduleAtFixedRate(new CleanTempDirRunnable(gitProxyProperties), 0, 5, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(new CleanZipFileRunnable(gitProxyProperties), 0, 1, TimeUnit.HOURS);
    }
}
