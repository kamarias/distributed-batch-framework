package io.github.kamarias.dbf.registry;

import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 抽象注册服务类，实现了注册服务的基本功能
 */
public abstract class AbstractRegistryService implements RegistryService {


    // 存储执行器实例的映射，用于快速检索和管理
    private final Map<String, ExecutorInstance> executorInstanceMap = new ConcurrentHashMap<>();

    // 执行器实例的总数量，用于快速获取总数
    private final AtomicLong executorCount = new AtomicLong(0);

    // 按应用统计的执行器数量，用于快速获取特定应用的执行器数量
    private final Map<String, Long> appExcecutorCount = new ConcurrentHashMap<>(0);

    // 按应用和组统计的执行器数量，用于快速获取特定应用和组的执行器数量
    private final Map<String, Long> appGroupExcecutorCount = new ConcurrentHashMap<>(0);


    /**
     * 注册执行器实例
     *
     * @param instance 要注册的执行器实例
     */
    @Override
    public void register(ExecutorInstance instance) {
        int startingSize = executorInstanceMap.size();
        executorInstanceMap.put(instance.getExecutorName(), instance);
        int endSize = executorInstanceMap.size();
        if (startingSize != endSize) {
            maintainCount(instance, true);
            instance.setHealthy(true);
        }
    }

    /**
     * 取消注册执行器实例
     *
     * @param instance 要取消注册的执行器实例
     */
    @Override
    public void unregister(ExecutorInstance instance) {
        ExecutorInstance item = executorInstanceMap.remove(instance.getExecutorName());
        if (Objects.nonNull(item)) {
            maintainCount(instance, false);
            instance.setHealthy(false);
        }
    }


    /**
     * 维护执行器数量信息
     *
     * @param instance 执行器实例
     * @param isAdd    是否添加操作，true表示添加，false表示移除
     */
    private void maintainCount(ExecutorInstance instance, boolean isAdd) {
        Assert.notNull(instance, "instance must not be null");
        if (isAdd) {
            executorCount.incrementAndGet();
            appExcecutorCount.put(instance.getAppId(), appExcecutorCount.getOrDefault(instance.getAppId(), 0L) + 1);
            appGroupExcecutorCount.put(getAppGroupKey(instance.getAppId(), instance.getGroupId()),
                    appExcecutorCount.getOrDefault(getAppGroupKey(instance.getAppId(), instance.getGroupId())
                            , 0L) + 1);
        } else {
            executorCount.decrementAndGet();
            appExcecutorCount.put(instance.getAppId(), appExcecutorCount.getOrDefault(instance.getAppId(), 0L) - 1);
            appGroupExcecutorCount.put(getAppGroupKey(instance.getAppId(), instance.getGroupId()),
                    appExcecutorCount.getOrDefault(getAppGroupKey(instance.getAppId(), instance.getGroupId())
                            , 0L) - 1);
        }
    }

    /**
     * 获取指定应用的执行器数量
     *
     * @param appId 应用ID
     * @return 执行器数量
     */
    @Override
    public long getExecutorCount(String appId) {
        return appExcecutorCount.get(appId);
    }

    /**
     * 获取所有执行器的总数量
     *
     * @return 执行器总数量
     */
    @Override
    public long getExecutorCount() {
        return executorCount.get();
    }

    /**
     * 获取指定应用和组的执行器数量
     *
     * @param appId   应用ID
     * @param groupId 组ID
     * @return 执行器数量
     */
    @Override
    public long getExecutorCount(String appId, String groupId) {
        return appGroupExcecutorCount.get(getAppGroupKey(appId, groupId));
    }


    /**
     * 构建应用和组的复合键
     *
     * @param appId   应用ID
     * @param groupId 组ID
     * @return 复合键字符串
     */
    private String getAppGroupKey(String appId, String groupId) {
        return appId + "&" + groupId;
    }

}
