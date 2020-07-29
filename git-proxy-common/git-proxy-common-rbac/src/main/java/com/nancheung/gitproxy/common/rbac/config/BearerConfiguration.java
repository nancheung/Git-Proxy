package com.nancheung.gitproxy.common.rbac.config;

import com.nancheung.gitproxy.common.rbac.bearer.AbstractBearerAuthenticationProvider;
import com.nancheung.gitproxy.common.rbac.bearer.BearerAuthenticationFilter;
import com.nancheung.gitproxy.common.rbac.bearer.BearerAuthenticationManager;
import com.nancheung.gitproxy.common.rbac.service.GitProxyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * JWT安全策略配置
 *
 * @author NanCheung
 */
@AllArgsConstructor
@Configuration
public class BearerConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public AuthenticationManager bearerAuthenticationManager(AbstractBearerAuthenticationProvider authenticationProvider) {
        return new BearerAuthenticationManager(authenticationProvider);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public BearerAuthenticationFilter bearerAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new BearerAuthenticationFilter(authenticationManager);
    }
    
    @Bean
    public BearerWebSecurityConfiguration bearerWebSecurityConfiguration(
            GitProxyUserDetailsService userDetailsService, AuthenticationEntryPoint authenticationEntryPoint,
            AuthenticationFailureHandler authenticationFailureHandler, AuthenticationSuccessHandler authenticationSuccessHandler,
            BearerAuthenticationFilter jwtAuthenticationFilter) {
        return new BearerWebSecurityConfiguration(userDetailsService, authenticationEntryPoint,
                authenticationFailureHandler, authenticationSuccessHandler,
                jwtAuthenticationFilter);
    }
    
}
