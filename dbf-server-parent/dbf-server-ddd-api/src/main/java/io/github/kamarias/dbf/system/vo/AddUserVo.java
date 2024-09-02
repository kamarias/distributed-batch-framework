package io.github.kamarias.dbf.system.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/13 15:21
 */
public class AddUserVo implements Serializable {

    /**
     * 用户昵称
     */
    private String nickName;


    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    private String account;


    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 电话号码
     */
    @NotBlank(message = "电话号码不能为空")
    private String phone;

    /**
     * 密码
     */
    @NotBlank(message = "电话号码不能为空")
    private String passWord;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 备注
     */
    private String remark;


    /**
     * 角色Id的集合
     */
    private List<String> roleIds;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public @NotBlank(message = "账号不能为空") String getAccount() {
        return account;
    }

    public void setAccount(@NotBlank(message = "账号不能为空") String account) {
        this.account = account;
    }

    public @NotBlank(message = "邮箱不能为空") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "邮箱不能为空") String email) {
        this.email = email;
    }

    public @NotBlank(message = "电话号码不能为空") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "电话号码不能为空") String phone) {
        this.phone = phone;
    }

    public @NotBlank(message = "电话号码不能为空") String getPassWord() {
        return passWord;
    }

    public void setPassWord(@NotBlank(message = "电话号码不能为空") String passWord) {
        this.passWord = passWord;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }
}
