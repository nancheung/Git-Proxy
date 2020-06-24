package com.nancheung.gitproxy.common.swagger;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * swagger配置类
 *
 * @author NanCheung
 */
@Slf4j
@AllArgsConstructor
@EnableSwagger2
@EnableAutoConfiguration
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
public class SwaggerAutoConfiguration {
    
    /**
     * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
     */
    private static final List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**");
    private static final String BASE_PATH = "/**";
    
    private final SwaggerProperties swaggerProperties;
    private final Environment environment;
    
    @Bean
    public Docket api() {
        // base-path处理
        List<String> basePath = swaggerProperties.getBasePath();
        if (basePath.isEmpty()) {
            basePath.add(BASE_PATH);
        }
        List<Predicate<String>> basePaths = basePath.stream().map(PathSelectors::ant).collect(Collectors.toList());
        
        // exclude-path处理
        List<String> excludePath = swaggerProperties.getExcludePath();
        if (excludePath.isEmpty()) {
            excludePath.addAll(DEFAULT_EXCLUDE_PATH);
        }
        List<Predicate<String>> excludePaths = excludePath.stream().map(PathSelectors::ant).collect(Collectors.toList());
        
        //noinspection Guava
        return new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo(swaggerProperties)).select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(Predicates.and(Predicates.not(Predicates.or(excludePaths)), Predicates.or(basePaths)))
                .build()
                .securitySchemes(Collections.singletonList(this.oauthSecuritySchema()))
                .securityContexts(Collections.singletonList(this.securityContext()))
                .pathMapping("/");
    }
    
    @Order
    @Bean
    public CommandLineRunner docAddressTip() {
        return commandLineRunner -> {
            if (log.isInfoEnabled()) {
                String hostAddress = InetAddress.getLocalHost().getHostAddress();
                String port = environment.getProperty("local.server.port");
                log.info("接口文档地址：http://{}:{}/doc.html", hostAddress, port);
            }
        };
    }
    
    /**
     * 配置默认的全局鉴权策略的开关，通过正则表达式进行匹配；默认匹配所有URL
     *
     * @return 安全上下文
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(this.defaultAuth())
                .forPaths(PathSelectors.regex(swaggerProperties.getAuthorization().getAuthRegex()))
                .build();
    }
    
    /**
     * 默认的全局鉴权策略
     *
     * @return 默认策略列表
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = swaggerProperties.getAuthorization().getAuthorizationScopeList().stream()
                .map(authorizationScope -> new AuthorizationScope(authorizationScope.getScope(), authorizationScope.getDescription()))
                .toArray(AuthorizationScope[]::new);
        return Collections.singletonList(SecurityReference.builder()
                .reference(swaggerProperties.getAuthorization().getName())
                .scopes(authorizationScopes)
                .build());
    }
    
    /**
     * 基于OAuth的安全方案
     *
     * @return OAuth安全方案
     */
    private OAuth oauthSecuritySchema() {
        SwaggerProperties.Authorization authorization = swaggerProperties.getAuthorization();
        
        List<AuthorizationScope> authorizationScopeList = authorization.getAuthorizationScopeList().stream()
                .map(authorizationScope -> new AuthorizationScope(authorizationScope.getScope(), authorizationScope.getDescription()))
                .collect(Collectors.toList());
        
        List<GrantType> grantTypes = authorization.getTokenUrlList().stream()
                .map(ResourceOwnerPasswordCredentialsGrant::new)
                .collect(Collectors.toList());
        
        return new OAuth(authorization.getName(), authorizationScopeList, grantTypes);
    }
    
    /**
     * 构建api信息
     *
     * @param swaggerProperties swagger参数配置
     * @return api信息
     */
    private static ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(), swaggerProperties.getContact().getEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }
    
}
