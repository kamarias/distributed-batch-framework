package io.github.kamarias.dbf.registry;

import java.io.Serializable;
import java.util.Map;

/**
 * 执行器实例接口，定义了与执行器相关的属性和方法
 */
public interface ExecutorInstance extends Serializable {

    /**
     * 获取执行器名称
     *
     * @return 执行器名称
     */
    String getExecutorName();

    /**
     * 获取执行器ID
     *
     * @return 执行器ID
     */
    String getExecutorId();

    /**
     * 获取应用ID
     *
     * @return 应用ID
     */
    String getAppId();

    /**
     * 获取组ID
     *
     * @return 组ID
     */
    String getGroupId();

    /**
     * 默认方法，用于判断执行器实例是否健康
     * 默认实现认为执行器实例是健康的
     *
     * @return true，如果执行器实例是健康的；否则为false
     */
    default boolean isHealthy() {
        return true;
    }

    /**
     * 设置执行器实例的健康状态
     *
     * @param healthy 执行器实例的健康状态，true表示健康，false表示不健康
     */
    void setHealthy(boolean healthy);

    /**
     * 获取执行器实例的元数据
     * 元数据可能包括与执行器相关的各种信息
     *
     * @return 执行器实例的元数据，键值对形式
     */
    Map<String, String> getMetadata();

}
