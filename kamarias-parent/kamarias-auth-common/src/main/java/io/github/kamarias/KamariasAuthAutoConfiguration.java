package io.github.kamarias;

import io.github.kamarias.aspect.AuthorizeVerificationAspect;
import io.github.kamarias.config.KamariasSecurityConfig;
import io.github.kamarias.filter.DefaultJwtAuthTokenFilter;
import io.github.kamarias.handler.DefaultUnauthorizedEntryPoint;
import io.github.kamarias.handler.DefaultUserDetailsService;
import io.github.kamarias.properties.SecurityProperties;
import io.github.kamarias.properties.TokenProperties;
import io.github.kamarias.token.AuthTokenService;
import io.github.kamarias.token.RedisAuthTokenService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;


@ConditionalOnClass(WebSecurityConfigurer.class)
@Import({TokenProperties.class, SecurityProperties.class})
public class KamariasAuthAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(AuthenticationEntryPoint.class)
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new DefaultUnauthorizedEntryPoint();
    }

    @Bean
    public DefaultJwtAuthTokenFilter defaultJwtAuthTokenFilter(AuthTokenService tokenService, SecurityProperties securityProperties) {
        return new DefaultJwtAuthTokenFilter(tokenService, securityProperties);
    }

    @Bean
    public AuthTokenService authTokenService(RedisTemplate redisTemplate, TokenProperties properties) {
        return new RedisAuthTokenService(redisTemplate, properties);
    }


    @Bean
    @ConditionalOnClass(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsService();
    }

    @Bean
    public AuthorizeVerificationAspect authorizeVerificationAspect() {
        return new AuthorizeVerificationAspect();
    }

}
