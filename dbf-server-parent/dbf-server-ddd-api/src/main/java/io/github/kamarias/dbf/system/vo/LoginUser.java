package io.github.kamarias.dbf.system.vo;

import io.github.kamarias.bean.AuthLogin;

import java.io.Serializable;


public class LoginUser extends AuthLogin implements Serializable {

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

}
