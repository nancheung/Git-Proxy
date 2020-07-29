package com.nancheung.gitproxy.common.rbac.bearer;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * 处理{@link BearerAuthenticationToken}的抽象类，提供身份验证功能
 *
 * @author NanCheung
 * @see AbstractUserDetailsAuthenticationProvider
 */
@Getter
@Setter
@Slf4j
public abstract class AbstractBearerAuthenticationProvider implements AuthenticationProvider {
    /**
     * 强制主体为字符串
     */
    private boolean forcePrincipalAsString = false;
    /**
     * 是否隐藏“UserNotFoundException”的异常信息
     */
    protected boolean hideUserNotFoundExceptions = true;
    /**
     * 将存储中加载的权限转换为在{@code Authentication}对象中使用的权限
     */
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    
    @Override
    public BearerAuthenticationToken authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(BearerAuthenticationToken.class, authentication, "仅支持BearerAuthenticationToken");
        
        // 确定username
        String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
                : authentication.getName();
        
        UserDetails user;
        try {
            user = this.retrieveUser(username, (BearerAuthenticationToken) authentication);
        } catch (UsernameNotFoundException e) {
            log.debug("用户[{}]没找到", username);
            
            // 隐藏用户找不到的信息，将抛出凭据错误的异常
            if (hideUserNotFoundExceptions) {
                throw new BadCredentialsException("错误的凭据");
            }
            
            throw e;
        }
        
        Object principalToReturn = user;
        
        if (forcePrincipalAsString) {
            principalToReturn = user.getUsername();
        }
        
        return this.createSuccessAuthentication(principalToReturn, authentication, user);
    }
    
    /**
     * 创建成功的认证信息
     *
     * @param principal      主体，应该是返回对象中的主体（由{@link #isForcePrincipalAsString())}方法定义）
     * @param authentication 交给provider进行验证的
     * @param user           由子类实现的
     * @return 成功的认证信息
     */
    protected BearerAuthenticationToken createSuccessAuthentication(Object principal,
                                                                    Authentication authentication, UserDetails user) {
        BearerAuthenticationToken result = new BearerAuthenticationToken(
                principal, authentication.getCredentials(),
                authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());
        
        return result;
    }
    
    /**
     * 查找用户
     *
     * @param username       需要检索的username
     * @param authentication 认证信息，子类的身份验证请求<em>可能</em>需要对<code>UserDetails</code>执行基于绑定的检索
     * @return 用户信息（绝不为<code>null</code>-而是应抛出异常）
     * @throws AuthenticationException {@link UsernameNotFoundException}将依据
     *                                 {@link #hideUserNotFoundExceptions}来处理
     */
    protected abstract UserDetails retrieveUser(String username,
                                                BearerAuthenticationToken authentication)
            throws AuthenticationException;
    
    @Override
    public boolean supports(Class<?> authentication) {
        return BearerAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
