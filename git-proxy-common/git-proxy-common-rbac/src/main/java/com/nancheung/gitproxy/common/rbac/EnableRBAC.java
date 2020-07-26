package com.nancheung.gitproxy.common.rbac;

import com.nancheung.gitproxy.common.rbac.config.WebSecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用RBAC模型的权限管理
 *
 * @author NanCheung
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(WebSecurityAutoConfiguration.class)
public @interface EnableRBAC {
}
