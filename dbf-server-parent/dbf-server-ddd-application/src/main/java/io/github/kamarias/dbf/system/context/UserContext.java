package io.github.kamarias.dbf.system.context;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserContext {

    /**
     * 用户Id
     */
    private String id;

    /**
     * 用户昵称
     */
    private String nickName;


    /**
     * 账号
     */
    private String account;


    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 密码
     */
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
     * 生日
     */
    private LocalDate birthday;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;


    /**
     * 创建人
     */
    private String createBy;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
