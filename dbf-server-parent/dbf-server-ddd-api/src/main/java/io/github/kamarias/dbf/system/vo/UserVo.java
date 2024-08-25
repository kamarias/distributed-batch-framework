package io.github.kamarias.dbf.system.vo;

import com.alibaba.fastjson2.JSON;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户信息表
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/4 10:58
 */
@Data
@Builder
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

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
