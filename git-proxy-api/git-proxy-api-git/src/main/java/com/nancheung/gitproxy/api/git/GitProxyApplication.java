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
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public static void main(String[] args) {
        SpringApplication.run(GitProxyApplication.class, args);
    }
    
    @Bean
    public GitProxyUserDetailsService gitProxyUserDetailsService() {
        return username -> User.builder()
                .username(username)
                .password(passwordEncoder.encode("{bcrypt}" + username))
                .authorities(username + "|authorities")
                .build();
    }
    
}
