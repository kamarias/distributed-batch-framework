package io.github.kamarias.dbf.zk;

import java.util.Objects;

public enum DbfConstantEnum {


    LEADER("/leader", "dbf 主节点选取路径"),


    SCHEDULE("/schedule", "框架调度执行器根节点"),


    EXECUTE("/execute", "执行器执行根节点"),


    DELETED("", "Deleted");

    /**
     * 枚举值
     */
    private final String value;

    /**
     * 枚举描述
     */
    private final String desc;


    DbfConstantEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    /**
     * 根据枚举项的值获取枚举项
     *
     * @param value 枚举项的值
     * @return 枚举项
     */
    public static DbfConstantEnum fromValue(String value) {
        for (DbfConstantEnum status : DbfConstantEnum.values()) {
            if (Objects.equals(status.getValue(), value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }


    public String getDesc() {
        return desc;
    }
}
