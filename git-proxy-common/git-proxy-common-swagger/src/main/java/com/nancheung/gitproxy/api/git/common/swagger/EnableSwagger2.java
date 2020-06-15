package com.nancheung.gitproxy.api.git.common.swagger;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启swagger
 *
 * @author NanCheung
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfiguration.class})
public @interface EnableSwagger2 {
}
