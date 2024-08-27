package io.github.kamarias.dbf.system.context;

import com.alibaba.fastjson2.JSON;

import java.util.Set;

public class LoginUserContext {


    /**
     * 用户唯一Id（启用单点登录时，此字段不能为空）
     */
    private String id;
    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 登录用户名
     */
    private String nickName;

    /**
     * 账号
     */
    private String account;


    /**
     * 登录邮箱地址
     */
    private String email;


    /**
     * 电话号码
     */
    private String phone;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
