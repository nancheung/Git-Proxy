package com.nancheung.gitproxy.common.rbac.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nancheung.gitproxy.common.rbac.enums.RbacClientExceptionEnum;
import com.nancheung.gitproxy.common.rbac.util.JwtTokenProvider;
import com.nancheung.gitproxy.common.restful.ApiResult;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 安全策略自动配置类
 *
 * @author NanCheung
 */
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(WebSecurityConfiguration.class)
public class WebSecurityAutoConfiguration {
    
    private final ObjectMapper objectMapper;
    
    /**
     * 未登录异常方案
     *
     * @return 未登录抛出异常 {@link RbacClientExceptionEnum#UNAUTHORIZED}
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            ApiResult<Void> result = ApiResult.failed(RbacClientExceptionEnum.UNAUTHORIZED, authException.getLocalizedMessage());
            
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(result));
        };
    }
    
    /**
     * 授权失败处理器
     *
     * @return 授权失败抛出异常 {@link RbacClientExceptionEnum#AUTHORIZED_FAILED}
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            ApiResult<Void> result = ApiResult.failed(RbacClientExceptionEnum.AUTHORIZED_FAILED, exception.getLocalizedMessage());
            
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(result));
        };
    }
    
    /**
     * 授权成功处理器
     *
     * @return 序列化 {@link UserDetails}
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String token = JwtTokenProvider.createToken(authentication);
            
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(token));
        };
    }
    
    /**
     * 密码编码器
     * 使用bcrypt方式编码
     *
     * @return {@link PasswordEncoderFactories#createDelegatingPasswordEncoder}
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
