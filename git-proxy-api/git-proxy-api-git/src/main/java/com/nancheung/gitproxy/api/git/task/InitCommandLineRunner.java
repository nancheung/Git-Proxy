package com.nancheung.gitproxy.api.git.task;

import com.nancheung.gitproxy.api.git.GitProxyProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

/**
 * 初始化执行器
 *
 * @author NanCheung
 */
@Slf4j
@AllArgsConstructor
public class InitCommandLineRunner implements CommandLineRunner {
    
    private final GitProxyProperties gitProxyProperties;
    
    @Override
    public void run(String... args) {
        gitProxyProperties.getTempDirPath().toFile().mkdirs();
        gitProxyProperties.getZipFilePath().toFile().mkdirs();
        gitProxyProperties.getProgramFilePath().toFile().mkdirs();
    
        log.debug("初始化运行环境，运行配置[{}]", gitProxyProperties);
    }
}
