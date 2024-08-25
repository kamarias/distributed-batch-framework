package io.github.kamarias.dbf.system.vo;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/13 15:21
 */
@Data
@Builder
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

}
