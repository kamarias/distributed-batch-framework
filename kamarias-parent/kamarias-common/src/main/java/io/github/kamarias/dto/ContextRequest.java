package io.github.kamarias.dto;


import java.io.Serializable;

/**
 * 上下文响应实体
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/1/30 14:32
 */
public class ContextRequest<E> implements Serializable {


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

    private ContextRequest(E data) {
        this.data = data;
    }

    public static <E> ContextRequest<E> create(E e) {
        return new ContextRequest<>(e);
    }

}
