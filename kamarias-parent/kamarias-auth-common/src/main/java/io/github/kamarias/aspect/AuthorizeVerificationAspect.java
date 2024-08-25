package io.github.kamarias.aspect;


import com.alibaba.fastjson2.JSON;
import io.github.kamarias.annotation.RequiresPermissions;
import io.github.kamarias.annotation.RequiresRoles;
import io.github.kamarias.dto.AjaxResult;
import io.github.kamarias.enums.LogicalEnum;
import io.github.kamarias.utils.SecurityUtils;
import io.github.kamarias.utils.http.ServletUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * 授权认证切面
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/16 9:18
 */
@Aspect
public class AuthorizeVerificationAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizeVerificationAspect.class);

    public AuthorizeVerificationAspect() {
    }

    @Pointcut("@annotation(io.github.kamarias.annotation.RequiresRoles)" +
            " || @annotation(io.github.kamarias.annotation.RequiresPermissions)")
    public void authorizeVerificationPointcut() {
    }

    @Around("authorizeVerificationPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (Objects.isNull(SecurityUtils.getLoginUser())) {
            ServletUtils.renderString(ServletUtils.getResponse(), JSON.toJSONString(new AjaxResult<>(HttpStatus.UNAUTHORIZED.value(), "用户未登录")));
            return null;
        }
        // 顺序不能乱，应该先角色在权限
        RequiresRoles requiresRoles = AnnotationUtils.getAnnotation(method, RequiresRoles.class);
        if (Objects.nonNull(requiresRoles)) {
            if (!checkRole(requiresRoles)) {
                ServletUtils.renderString(ServletUtils.getResponse(), JSON.toJSONString(new AjaxResult<>(HttpStatus.SERVICE_UNAVAILABLE.value(), "用户未授权")));
                return null;
            }
        }
        RequiresPermissions requiresPermissions = AnnotationUtils.getAnnotation(method, RequiresPermissions.class);
        if (Objects.nonNull(requiresPermissions)) {
            if (!checkPermissions(requiresPermissions)) {
                ServletUtils.renderString(ServletUtils.getResponse(), JSON.toJSONString(new AjaxResult<>(HttpStatus.SERVICE_UNAVAILABLE.value(), "用户未授权")));
                return null;
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 校验角色
     *
     * @param requiresRoles 权限注解
     */
    private boolean checkRole(RequiresRoles requiresRoles) {
        if (LogicalEnum.AND.equals(requiresRoles.logical())) {
            return checkRoleAnd(requiresRoles.value());
        } else {
            return checkRoleOr(requiresRoles.value());
        }
    }

    /**
     * 校验权限
     *
     * @param requiresPermissions 权限注解
     */
    private boolean checkPermissions(RequiresPermissions requiresPermissions) {
        if (LogicalEnum.AND.equals(requiresPermissions.logical())) {
            return checkPermissionsAnd(requiresPermissions.value());
        } else {
            return checkPermissionsOr(requiresPermissions.value());
        }
    }

    /**
     * 校验角色且条件
     *
     * @param roles 需要校验的角色集合
     */
    private boolean checkRoleAnd(String... roles) {
        Set<String> rolesList = SecurityUtils.getLoginUser().getRoles();
        for (String role : roles) {
            if (!hasRole(role, rolesList)) {
                LOGGER.warn("校验角色异常：{}", roles);
                return false;
            }
        }
        return true;
    }

    /**
     * 校验权限且条件
     *
     * @param permissions 权限集合
     */
    private boolean checkPermissionsAnd(String... permissions) {
        Set<String> permissionsList = SecurityUtils.getLoginUser().getPermissions();
        for (String permission : permissions) {
            if (!hasPermissions(permission, permissionsList)) {
                LOGGER.warn("校验权限异常：{}", permission);
                return false;
            }
        }
        return true;
    }

    /**
     * 校验角色或条件
     *
     * @param roles 角色集合
     */
    private boolean checkRoleOr(String... roles) {
        Set<String> rolesList = SecurityUtils.getLoginUser().getRoles();
        for (String role : roles) {
            if (hasRole(role, rolesList)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验权限或条件
     *
     * @param permissions 权限集合
     */
    private boolean checkPermissionsOr(String... permissions) {
        Set<String> permissionsList = SecurityUtils.getLoginUser().getPermissions();
        for (String permission : permissions) {
            if (hasPermissions(permission, permissionsList)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 角色匹配-支持*匹配
     *
     * @param role  匹配的角色编码
     * @param roles 角色编码集合
     * @return 返回匹配结果
     */
    private boolean hasRole(String role, Collection<String> roles) {
        return roles.stream().parallel().anyMatch(x -> PatternMatchUtils.simpleMatch(x, role));
    }

    /**
     * 匹配权限-支持*匹配
     *
     * @param permissions     匹配的权限编码
     * @param permissionsList 权限编码集合
     * @return 返回匹配结果
     */
    private boolean hasPermissions(String permissions, Collection<String> permissionsList) {
        return permissionsList.stream().parallel().anyMatch(x -> PatternMatchUtils.simpleMatch(x, permissions));
    }

}
