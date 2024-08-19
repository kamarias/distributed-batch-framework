package io.github.kamarias.dbf.registry;

import java.util.Map;

/**
 * 默认的执行器实例实现类
 * 用于在注册中心中表示和管理单个执行器的状态和元数据信息
 */
public class DefaultExecutorInstance implements ExecutorInstance {


    // 执行器所属的应用ID
    private final String appId;

    // 执行器所属的组ID
    private final String groupId;

    // 执行器的唯一标识
    private final String executorId;

    // 表示执行器是否健康的标志
    private boolean healthy = false;

    // 执行器的元数据信息
    private final Map<String, String> metadata;

    /**
     * 构造函数，初始化执行器实例
     *
     * @param appId      执行器所属的应用ID
     * @param groupId    执行器所属的组ID
     * @param executorId 执行器的唯一标识
     * @param metadata   执行器的元数据信息
     */
    public DefaultExecutorInstance(String appId, String groupId, String executorId, Map<String, String> metadata) {
        this.executorId = executorId;
        this.appId = appId;
        this.groupId = groupId;
        this.metadata = metadata;
    }

    /**
     * 获取执行器名称
     * 名称由应用ID、组ID和执行器ID组合而成，用于唯一确定一个执行器
     *
     * @return 执行器名称
     */
    @Override
    public String getExecutorName() {
        return appId + "&" + groupId + "&" + executorId;
    }

    /**
     * 获取执行器的唯一标识
     *
     * @return 执行器ID
     */
    @Override
    public String getExecutorId() {
        return executorId;
    }

    /**
     * 获取执行器所属的应用ID
     *
     * @return 应用ID
     */
    @Override
    public String getAppId() {
        return appId;
    }

    /**
     * 获取执行器所属的组ID
     *
     * @return 组ID
     */
    @Override
    public String getGroupId() {
        return groupId;
    }

    /**
     * 判断执行器是否健康
     *
     * @return true表示健康，false表示不健康
     */
    @Override
    public boolean isHealthy() {
        return healthy;
    }

    /**
     * 设置执行器的健康状态
     *
     * @param healthy true表示健康，false表示不健康
     */
    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    /**
     * 获取执行器的元数据信息
     *
     * @return 执行器的元数据信息
     */
    @Override
    public Map<String, String> getMetadata() {
        return metadata;
    }

    @Override
    public String toString() {
        return "DefaultExecutorInstance{" +
                "appId='" + appId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", executorId='" + executorId + '\'' +
                ", healthy=" + healthy +
                ", metadata=" + metadata +
                '}';
    }
}
