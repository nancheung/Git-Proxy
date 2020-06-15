package com.nancheung.gitproxy.api.git;

import com.nancheung.gitproxy.api.git.common.swagger.EnableSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Git代理
 *
 * @author NanCheung
 */
@EnableSwagger2
@EnableAsync
@EnableConfigurationProperties(GitProxyProperties.class)
@SpringBootApplication
public class GitProxyApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GitProxyApplication.class, args);
    }
    
}
