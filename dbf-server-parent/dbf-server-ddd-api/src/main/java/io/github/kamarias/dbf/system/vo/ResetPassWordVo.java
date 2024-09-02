package io.github.kamarias.dbf.system.vo;

import com.alibaba.fastjson2.JSON;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ResetPassWordVo implements Serializable {

    /**
     * Id
     */
    @NotBlank(message = "id不能为空")
    private String id;

    /**
     * 密码
     */
    @NotBlank(message = "重置密码不能为空")
    private String passWord;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
