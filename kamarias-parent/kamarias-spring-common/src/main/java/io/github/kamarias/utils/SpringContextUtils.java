package io.github.kamarias.utils;

import io.github.kamarias.utils.string.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@ConditionalOnMissingBean(SpringContextUtils.class)
public class SpringContextUtils implements BeanFactoryPostProcessor, ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ConfigurableListableBeanFactory beanFactory;


    private static ApplicationContext applicationContext;


    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    public static <T> T getBean(Class<T> clz) throws BeansException {
        return (T) beanFactory.getBean(clz);
    }

    public static void registerSingleton(String beanName, Object singletonObject) {
        beanFactory.registerSingleton(beanName, singletonObject);
    }

    public static void registerAlias(String beanName, String alias) throws BeanDefinitionStoreException {
        beanFactory.registerAlias(beanName, alias);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

    /**
     * 获取aop代理对象
     *
     * @param invoker
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }

    /**
     * 获取当前的环境配置，无配置返回null
     *
     * @return 当前的环境配置
     */
    public static String[] getActiveProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     *
     * @return 当前的环境配置
     */
    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return StringUtils.isNotEmpty(activeProfiles) ? activeProfiles[0] : null;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringContextUtils.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }
}
