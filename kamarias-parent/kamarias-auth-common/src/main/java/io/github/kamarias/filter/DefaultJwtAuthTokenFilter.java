package io.github.kamarias.filter;

import io.github.kamarias.bean.AuthLogin;
import io.github.kamarias.properties.SecurityProperties;
import io.github.kamarias.token.AuthTokenService;
import io.github.kamarias.utils.http.ServletUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 每次请求过滤器，一般用于注入上下文信息
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/23 15:47
 */
public class DefaultJwtAuthTokenFilter extends OncePerRequestFilter {

    private final AuthTokenService authTokenService;

    private final SecurityProperties securityProperties;

    public DefaultJwtAuthTokenFilter(AuthTokenService redisTokenService, SecurityProperties securityProperties) {
        this.authTokenService = redisTokenService;
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        AuthLogin token = null;
        // 白名单不解析
        if (securityProperties.getAnonymous().contains(ServletUtils.getNotContextPathRequestURI(request))) {
            chain.doFilter(request, response);
        } else {
            try {
                token = authTokenService.analyzeToken();
            } catch (Exception e) {
            }
            if (Objects.isNull(token)) {
                // 不给授权，直接放行（会被授权拦截器拦截）
                chain.doFilter(request, response);
            } else {
                authorize(token);
                chain.doFilter(request, response);
            }
        }
    }

    /**
     * 授权访问
     *
     * @param o   继承UuidObject的对象实体
     * @param <T> 继承UuidObject的泛型
     */
    private <T extends AuthLogin> void authorize(T o) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(o, null, null));
    }

}
