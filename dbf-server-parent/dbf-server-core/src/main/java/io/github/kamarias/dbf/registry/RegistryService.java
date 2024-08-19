package io.github.kamarias.dbf.registry;

import java.rmi.registry.Registry;

/**
 * 注册中心服务接口
 * 该接口定义了注册中心的基本操作，包括注册、注销执行器实例，以及获取不同粒度的执行器数量
 */
public interface RegistryService {


    /**
     * 注册一个执行器实例
     *
     * @param instance 要注册的执行器实例
     */
    void register(ExecutorInstance instance);


    /**
     * 注销一个执行器实例
     *
     * @param instance 要注销的执行器实例
     */
    void unregister(ExecutorInstance instance);

    /**
     * 获取所有执行器的数量
     *
     * @return 所有执行器的数量
     */
    long getExecutorCount();

    /**
     * 根据应用ID获取执行器的数量
     *
     * @param appId 应用的ID
     * @return 指定应用ID的执行器数量
     */
    long getExecutorCount(String appId);

    /**
     * 根据应用ID和组ID获取执行器的数量
     *
     * @param appId  应用的ID
     * @param groupId 组的ID
     * @return 指定应用ID和组ID的执行器数量
     */
    long getExecutorCount(String appId, String groupId);

}
