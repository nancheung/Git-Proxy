package com.nancheung.gitproxy.common.rbac.filter;

import com.nancheung.gitproxy.common.rbac.util.JwtTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT身份认证过滤器
 *
 * @author NanCheung
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            Authentication authentication = JwtTokenProvider.getAuthentication(token.substring(7));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        
        filterChain.doFilter(request, response);
    }
}
