package com.nancheung.gitproxy.api.git;

import com.nancheung.gitproxy.common.rbac.EnableRBAC;
import com.nancheung.gitproxy.common.rbac.service.GitProxyUserDetailsService;
import com.nancheung.gitproxy.common.swagger.EnableSwagger2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Git代理
 *
 * @author NanCheung
 */
@EnableRBAC
@EnableSwagger2
@EnableAsync
@EnableConfigurationProperties(GitProxyProperties.class)
@SpringBootApplication
public class GitProxyApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GitProxyApplication.class, args);
    }
    
    @Bean
    public GitProxyUserDetailsService gitProxyUserDetailsService() {
        return username -> User.builder()
                .username(username)
                .password("{bcrypt}$2a$10$MDlE5pxhvRVm8rQLnvt8O.oJM7VOeYaKmrRcIjw23N9wz7TXjRoFC")
                .authorities(username + "|authorities")
                .build();
    }
    
}
