package io.github.kamarias.dto;


import com.alibaba.fastjson2.JSONObject;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 上下文响应实体
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/1/30 14:32
 */
public class ContextResponse<E> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回码状态
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 消息体
     */
    private E data;

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static <E> ContextResponse<E> success() {
        return ContextResponse.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static <E> ContextResponse<E> success(E data) {
        return ContextResponse.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static <E> ContextResponse<E> success(String msg) {
        return ContextResponse.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <E> ContextResponse<E> success(String msg, E data) {
        return new ContextResponse<>(0, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return 操作结果
     */
    public static <E> ContextResponse<E> error() {
        return ContextResponse.error("操作失败");
    }

    /**
     * 返回服务器内部错误
     *
     * @param msg 错误信息
     */
    public static <E> ContextResponse<E> error(String msg) {
        return ContextResponse.error(msg, null);
    }

    /**
     * 返回服务器内部错误
     *
     * @param msg  错误信息
     * @param data 错误数据
     */
    public static <E> ContextResponse<E> error(String msg, E data) {
        return new ContextResponse<>(1, msg, data);
    }

    /**
     * 判断是否请求成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        if (this.getCode() == null) {
            return false;
        }
        return 0 == this.getCode();
    }

    /**
     * 判断是否请求成功
     *
     * @return 是否成功
     */
    public boolean isError() {
        return !isSuccess();
    }

    public Integer getCode() {
        return code;
    }

    private final void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public E getData() {
        return data;
    }

    private final void setData(E data) {
        this.data = data;
    }

    private ContextResponse(Integer code, String msg, E data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
