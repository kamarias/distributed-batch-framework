package io.github.kamarias.dto;


import java.io.Serializable;

/**
 * 上下文响应实体
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/1/30 14:32
 */
public class ContextResponse<E> implements Serializable {


    /**
     * 消息体
     */
    private E data;

    public E getData() {
        return data;
    }

    private void setData(E data) {
        this.data = data;
    }

    private ContextResponse(E data) {
        this.data = data;
    }

    public static <E> ContextResponse<E> create(E e) {
        return new ContextResponse<>(e);
    }

}
