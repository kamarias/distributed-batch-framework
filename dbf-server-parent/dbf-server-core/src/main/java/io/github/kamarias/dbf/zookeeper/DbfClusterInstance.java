package io.github.kamarias.dbf.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DbfClusterInstance {


    private static final Logger logger = LoggerFactory.getLogger(DbfClusterInstance.class);

    private static final Map<String, DbfApplicationContext> clusterMap = new ConcurrentHashMap<>();

    private static final String INSTANCE_ID = UUID.randomUUID().toString();

    private static final DbfClusterInstance INSTANCE = new DbfClusterInstance();

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

    public static DbfClusterInstance getInstance(){
        return INSTANCE;
    }

}
