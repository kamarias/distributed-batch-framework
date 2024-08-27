package io.github.kamarias.dbf.system.enums;

import io.github.kamarias.enums.DictEnum;

/**
 * 路由类型枚举
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/9 14:54
 */
public enum RouterTypeEnum implements DictEnum<Integer, String> {

    /**
     * 目录
     */
    DIRECTORY(0, "目录"),

    /**
     * 菜单
     */
    MENU(1, "菜单");

    /**
     * 编码
     */
    private final Integer key;

    /**
     * 描述
     */
    private final String value;

    RouterTypeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

}
