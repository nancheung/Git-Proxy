package com.nancheung.gitproxy.task;

import com.nancheung.gitproxy.GitProxyProperties;
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
        gitProxyProperties.getTempDir().toFile().mkdirs();
        gitProxyProperties.getZipFileDir().toFile().mkdirs();
        gitProxyProperties.getProgramFileDir().toFile().mkdirs();
    }
}
