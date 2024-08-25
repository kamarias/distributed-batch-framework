package io.github.kamarias.web;

import io.github.kamarias.web.config.KamariasWebConfigurer;
import io.github.kamarias.web.filter.RepeatableFilter;
import io.github.kamarias.web.interceptor.RepeatSubmitInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnClass(WebMvcConfigurer.class)
public class KamariasWebMvcAutoConfiguration {


    @Bean
    @ConditionalOnClass(HandlerInterceptor.class)
    public RepeatableFilter repeatableFilter() {
        return new RepeatableFilter();
    }


    @Bean
    @ConditionalOnClass(WebMvcConfigurer.class)
    public KamariasWebConfigurer kamariasWebConfigurer(RepeatSubmitInterceptor repeatSubmitInterceptor) {
        return new KamariasWebConfigurer(repeatSubmitInterceptor);
    }

    /**
     * 跨域配置
     */
    @Bean
    @ConditionalOnMissingBean(CorsFilter.class)
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        // config.addAllowedOrigin("*");
        config.addAllowedOriginPattern("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 设置跨域最长存在时间
        config.setMaxAge(3600L);
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
