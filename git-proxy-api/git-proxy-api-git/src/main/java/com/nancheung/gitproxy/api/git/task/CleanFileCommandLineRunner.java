package com.nancheung.gitproxy.api.git.task;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.nancheung.gitproxy.api.git.GitProxyProperties;
import com.nancheung.gitproxy.api.git.task.runnable.TempDirCleanRunnable;
import com.nancheung.gitproxy.api.git.task.runnable.ZipFileCleanRunnable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 清理文件执行器
 *
 * @author NanCheung
 */
@Slf4j
@AllArgsConstructor
public class CleanFileCommandLineRunner implements CommandLineRunner {
    
    private final GitProxyProperties gitProxyProperties;
    private final CleanStrategyProperties cleanStrategyProperties;
    
    @Override
    public void run(String... args) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1, ThreadFactoryBuilder.create().setNamePrefix("clean-pool-").build());
        executorService.scheduleAtFixedRate(new TempDirCleanRunnable(gitProxyProperties.getTempDirPath(), cleanStrategyProperties.getTempDir()), 0, 5, TimeUnit.MINUTES);
        executorService.scheduleAtFixedRate(new ZipFileCleanRunnable(gitProxyProperties.getZipFilePath(), cleanStrategyProperties.getZipFile()), 0, 1, TimeUnit.HOURS);
    
        log.debug("创建清理任务完成");
    }
}
