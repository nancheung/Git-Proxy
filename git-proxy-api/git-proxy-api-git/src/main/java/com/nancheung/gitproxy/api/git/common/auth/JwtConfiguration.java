package com.nancheung.gitproxy.api.git.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nancheung.gitproxy.common.rbac.bearer.AbstractBearerAuthenticationProvider;
import com.nancheung.gitproxy.common.rbac.bearer.BearerAuthenticationToken;
import com.nancheung.gitproxy.common.rbac.service.GitProxyUserDetailsService;
import com.nancheung.gitproxy.common.rbac.util.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 配置jwt
 *
 * @author NanCheung
 */
@Configuration
public class JwtConfiguration {
    
    @Value("${spring.profiles.active}")
    private String activeProfiles;
    
    @Bean
    public GitProxyUserDetailsService gitProxyUserDetailsService() {
        return username -> User.builder()
                .username(username)
                .password("{bcrypt}$2a$10$MDlE5pxhvRVm8rQLnvt8O.oJM7VOeYaKmrRcIjw23N9wz7TXjRoFC")
                .authorities(username + "|authorities")
                .build();
    }
    
    @Bean
    public AbstractBearerAuthenticationProvider authenticationProvider(GitProxyUserDetailsService userDetailsService) {
        AbstractBearerAuthenticationProvider authenticationProvider = new AbstractBearerAuthenticationProvider() {
            @Override
            protected UserDetails retrieveUser(String username, BearerAuthenticationToken authentication) throws AuthenticationException {
                Claims claims = JwtTokenProvider.parseToken((String) authentication.getCredentials());
                return userDetailsService.loadUserByUsername(claims.getSubject());
            }
        };
        if (!"prod".equals(activeProfiles)) {
            authenticationProvider.setHideUserNotFoundExceptions(false);
        }
        return authenticationProvider;
    }
    
    /**
     * 授权成功处理器
     *
     * @return 序列化 {@link UserDetails}
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthenticationSuccessHandler authenticationSuccessHandler(ObjectMapper objectMapper) {
        return (request, response, authentication) -> {
            String token = JwtTokenProvider.createToken(authentication);
            
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(token));
        };
    }
}
