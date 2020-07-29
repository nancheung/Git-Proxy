package com.nancheung.gitproxy.common.rbac.bearer;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 一个{@link org.springframework.security.core.Authentication}实现，用于简单表示Bearer token。
 *
 * @author NanCheung
 */
public class BearerAuthenticationToken extends AbstractAuthenticationToken {
    
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    
    private final Object credentials;
    private Object principal;
    
    public BearerAuthenticationToken(Object credentials) {
        super(null);
        this.credentials = credentials;
        setAuthenticated(false);
    }
    
    public BearerAuthenticationToken(Object credentials, Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.credentials = credentials;
        this.principal = principal;
        super.setAuthenticated(true);
    }
    
    @Override
    public Object getPrincipal() {
        return principal;
    }
    
    @Override
    public Object getCredentials() {
        return credentials;
    }
    
    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("无法将此令牌设置为可信任，请使用构造函数传递GrantedAuthority列表");
        }
        
        super.setAuthenticated(false);
    }
}
