package io.github.kamarias.bean;

import com.alibaba.fastjson2.JSON;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/2/23 9:53
 */
public abstract class AuthLogin implements AuthService, Serializable {

    /**
     * 用户唯一Id（启用单点登录时，此字段不能为空）
     */
    private String id;

    /**
     * 用户缓存token key
     */
    private String uuid = UUID.randomUUID().toString();

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;


    public String getId() {
        return id;
    }

    public final void setId(String id) {
        this.id = id;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    @Override
    public final void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public final void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
