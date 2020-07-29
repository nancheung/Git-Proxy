package com.nancheung.gitproxy.common.rbac.bearer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Bearer身份认证过滤器
 * <p>处理HTTP请求的BEARER授权标头，并将结果放入{@link SecurityContextHolder}中。</p>
 *
 * @author NanCheung
 */
@Slf4j
@AllArgsConstructor
public class BearerAuthenticationFilter extends OncePerRequestFilter implements RequestMatcher, AuthenticationConverter {
    public static final String AUTHENTICATION_SCHEME_BEARER = "Bearer";
    
    private final AuthenticationManager authenticationManager;
    /**
     * 以在验证时携带额外信息，默认携带remoteAddress和sessionId
     */
    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;
    
    public BearerAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.authenticationDetailsSource = new WebAuthenticationDetailsSource();
    }
    
    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.authenticationManager, "AuthenticationManager是必需的");
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!this.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        Authentication authentication = this.convert(request);
        Authentication authenticate = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        
        filterChain.doFilter(request, response);
    }
    
    @Override
    public boolean matches(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null) {
            return false;
        }
        return StringUtils.startsWithIgnoreCase(header, BearerAuthenticationFilter.AUTHENTICATION_SCHEME_BEARER);
    }
    
    @Override
    public Authentication convert(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        
        header = header.trim();
        if (header.equalsIgnoreCase(BearerAuthenticationFilter.AUTHENTICATION_SCHEME_BEARER)) {
            throw new BadCredentialsException("bearer token为空");
        }
        
        String token = header.substring(7);
        
        BearerAuthenticationToken authenticationToken = new BearerAuthenticationToken(token);
        authenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
        return authenticationToken;
    }
    
}
