package io.github.kamarias.dbf.system.vo;

import com.alibaba.fastjson2.JSONObject;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class IdVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @NotBlank(message = "id不能为空")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }


}
