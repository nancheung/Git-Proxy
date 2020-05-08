package com.nancheung.gitproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Git代理
 *
 * @author NanCheung
 */
@EnableAsync
@EnableConfigurationProperties(GitProxyProperties.class)
@SpringBootApplication
public class GitProxyApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GitProxyApplication.class, args);
    }
    
}
