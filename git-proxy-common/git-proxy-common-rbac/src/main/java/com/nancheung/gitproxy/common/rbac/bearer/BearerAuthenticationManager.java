package com.nancheung.gitproxy.common.rbac.bearer;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 处理{@link BearerAuthenticationToken}请求
 * <p>也可以直接使用默认的{@link ProviderManager}，实现{@link AuthenticationProvider}</p>
 *
 * @author NanCheung
 */
@AllArgsConstructor
public class BearerAuthenticationManager implements AuthenticationManager {
    
    private final AbstractBearerAuthenticationProvider authenticationProvider;
    
    @Override
    public BearerAuthenticationToken authenticate(Authentication authentication) throws AuthenticationException {
        return authenticationProvider.authenticate(authentication);
    }
}
