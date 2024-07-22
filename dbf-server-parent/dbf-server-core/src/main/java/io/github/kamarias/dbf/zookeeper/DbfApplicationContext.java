package io.github.kamarias.dbf.zookeeper;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;

public class DbfApplicationContext implements Serializable {

    private transient BeanDefinitionRegistry registry;

    private transient ConfigurableListableBeanFactory beanFactory;

    private transient ApplicationContext applicationContext;

    private String instanceId = DbfClusterInstance.getInstanceId();

    /**
     * 实例Ip
     */
    private String ip;

    /**
     * 通讯端口
     */
    private int port;

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 节点标签（默认）
     */
    private ClusterTags tags = ClusterTags.SALVE;

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }



    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public ClusterTags getTags() {
        return tags;
    }

    public void setTags(ClusterTags tags) {
        this.tags = tags;
    }

    /**
     * 集群标签
     */
    public enum ClusterTags {

        MASTER,

        SALVE;

    }

}
