package com.nancheung.gitproxy.common.rbac.config;

import com.nancheung.gitproxy.common.rbac.bearer.BearerAuthenticationFilter;
import com.nancheung.gitproxy.common.rbac.service.GitProxyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author NanCheung
 */
@AllArgsConstructor
public class BearerWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    private final GitProxyUserDetailsService userDetailsService;
    
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final BearerAuthenticationFilter authenticationFilter;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                // 禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 处理鉴权异常
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                // 优先执行自定义身份认证过滤器
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;
    }
    
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                // 不拦截knife4j的资源
                .antMatchers("/doc.html", "/favicon.ico", "/webjars/**")
                // 不拦截swagger的资源
                .antMatchers("/v2/api-docs", "/swagger-resources/**");
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
