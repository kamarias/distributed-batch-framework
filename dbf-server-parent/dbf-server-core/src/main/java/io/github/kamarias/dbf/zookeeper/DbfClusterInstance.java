package io.github.kamarias.dbf.zookeeper;

import com.alibaba.fastjson2.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.support.GenericApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class DbfClusterInstance {


    private static final Logger logger = LoggerFactory.getLogger(DbfClusterInstance.class);

    private static final Map<String, DbfApplicationContext> clusterMap = new ConcurrentHashMap<>();

    private static final String INSTANCE_ID = UUID.randomUUID().toString();

    private static final String PATH_SEPARATOR = "/";

    private static ConfigurableListableBeanFactory beanFactory;

    private DbfClusterInstance() {
    }

    public static void initialize(BeanDefinitionRegistry registry) {
        // prepare context and do customize
        DbfApplicationContext context = new DbfApplicationContext();

        // Spring ApplicationContext may not ready at this moment (e.g. load from xml), so use registry as key
        if (clusterMap.putIfAbsent(INSTANCE_ID, context) != null) {
            return;
        }

        // find beanFactory
        ConfigurableListableBeanFactory beanFactory = findBeanFactory(registry);
        DbfClusterInstance.beanFactory = beanFactory;

        // init dbf context
        initContext(context, registry, beanFactory);
    }


    // 初始化上下文
    private static void initContext(DbfApplicationContext context, BeanDefinitionRegistry registry, ConfigurableListableBeanFactory beanFactory) {
        context.setRegistry(registry);
        context.setBeanFactory(beanFactory);
//        context.setApplicationContext(beanFactory.getBean(ApplicationContext.class));

        // @TODO init 上下文其它属性


        // 将上下文注入到Spring中
        registerContextBeans(beanFactory, context);
    }

    /**
     * 刷新本地缓存的主从节点信息
     *
     * @param client zk 客户端
     * @param event  路径数据变动事件
     */
    public static void refreshLocalClusterMap(CuratorFramework client, PathChildrenCacheEvent event) {
        byte[] bytes = event.getData().getData();
        DbfApplicationContext context = JSON.parseObject(new String(bytes, StandardCharsets.UTF_8), DbfApplicationContext.class);
        DbfApplicationContext localContext = beanFactory.getBean(DbfApplicationContext.class);
        switch (event.getType()) {
            case CHILD_ADDED:
            case CHILD_UPDATED:
                if (INSTANCE_ID.equals(context.getInstanceId())) {
                    clusterMap.put(INSTANCE_ID, localContext);
                } else {
                    clusterMap.put(context.getInstanceId(), context);
                }
                break;
            case CHILD_REMOVED:
                if (INSTANCE_ID.equals(context.getInstanceId())) {
                    clusterMap.remove(INSTANCE_ID);
                } else {
                    clusterMap.remove(context.getInstanceId());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 从节点提升为主节点
     *
     * @param client zk 客户端
     * @return 返回处理结果
     */
    public static boolean refreshClusterMapToMaster(CuratorFramework client) {
        InterProcessMutex lock = new InterProcessMutex(client, DbfZookeeperEnum.INSTANCE_DATE_LOCK_PATH.getValue());
        try {
            lock.acquire(5, TimeUnit.SECONDS);
            if (Objects.nonNull(client.checkExists().forPath(DbfZookeeperEnum.INSTANCE_DATE.getValue() + PATH_SEPARATOR + DbfClusterInstance.INSTANCE_ID))) {
                DbfApplicationContext bean = beanFactory.getBean(DbfApplicationContext.class);
                // 刷新本地
                bean.setTags(DbfApplicationContext.ClusterTags.MASTER);
                clusterMap.put(DbfClusterInstance.INSTANCE_ID, bean);
                String contextString = JSON.toJSONString(bean);
                // 刷新远程
                client.setData().forPath(DbfZookeeperEnum.INSTANCE_DATE.getValue() + PATH_SEPARATOR + DbfClusterInstance.INSTANCE_ID, contextString.getBytes(StandardCharsets.UTF_8));
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("异常信息", e);
            return false;
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                logger.error("释放锁失败", e);
            }
        }
    }

    /**
     * 注册本地节点到远程
     *
     * @param client             zk 客户端
     * @param applicationContext 应用上下文
     * @return 返回注册结果
     */
    public static boolean registerClusterMap(CuratorFramework client, DbfApplicationContext applicationContext) {
        String contextString = JSON.toJSONString(applicationContext);
        // 注册节点信息
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                    .forPath(DbfZookeeperEnum.INSTANCE_DATE.getValue() + PATH_SEPARATOR + DbfClusterInstance.INSTANCE_ID,
                            contextString.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (Exception e) {
            logger.error("注册节点信息异常：", e);
            return false;
        }
    }

    private static void registerContextBeans(
            ConfigurableListableBeanFactory beanFactory, DbfApplicationContext context) {
        // register singleton
        registerSingleton(beanFactory, context);
    }


    private static void registerSingleton(ConfigurableListableBeanFactory beanFactory, Object bean) {
        beanFactory.registerSingleton(bean.getClass().getName(), bean);
    }


    private static ConfigurableListableBeanFactory findBeanFactory(BeanDefinitionRegistry registry) {
        ConfigurableListableBeanFactory beanFactory;
        if (registry instanceof ConfigurableListableBeanFactory) {
            beanFactory = (ConfigurableListableBeanFactory) registry;
        } else if (registry instanceof GenericApplicationContext) {
            GenericApplicationContext genericApplicationContext = (GenericApplicationContext) registry;
            beanFactory = genericApplicationContext.getBeanFactory();
        } else {
            throw new IllegalStateException("Can not find Spring BeanFactory from registry: "
                    + registry.getClass().getName());
        }
        return beanFactory;
    }

    /**
     * 获取机器的实例Id
     *
     * @return 返回实例Id
     */
    public static String getInstanceId() {
        return DbfClusterInstance.INSTANCE_ID;
    }


}
