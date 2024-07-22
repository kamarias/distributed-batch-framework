package io.github.kamarias.dbf.zookeeper;

public enum DbfZookeeperEnum {


    /**
     * 存放注册的主节点数据
     */
    LEADER_PATH("/leader_latch", "This is Distributed Batch Framework leader path"),


    /**
     * 存放注册的所有实例数据父节点
     */
    INSTANCE_DATE("/instance_date", "存放注册节点的数据"),


    /**
     * 更新实例数据锁 key
     */
    INSTANCE_DATE_LOCK_PATH("/lock_path", "锁路径");


    /**
     * 节点路径
     */
    private final String value;

    /**
     * 节点描述
     */
    private final String desc;


    DbfZookeeperEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
