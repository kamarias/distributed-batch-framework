package io.github.kamarias.dbf.system.vo;

import io.github.kamarias.bean.AuthLogin;

import java.io.Serializable;

public class LoginUser extends AuthLogin implements Serializable {

    /**
     * 登录用户名
     */
    private String name;

    /**
     * 登录邮箱地址
     */
    private String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
