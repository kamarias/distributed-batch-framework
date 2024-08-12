package io.github.kamarias.dbf.zk;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = DbfZookeeperProperties.PREFIX)
public class DbfZookeeperProperties {


    public final static String PREFIX = "dbf.zookeeper";

    /**
     * 集群地址
     */
    private String address = "127.0.0.1:2181";

    /**
     * 连接超时时间
     */
    private Integer connectionTimeoutMs = 10000;


    /**
     * 会话超时时间
     */
    private Integer sessionTimeOut = 1000;


    /**
     * 重试机制时间参数
     */
    private Integer sleepMsBetweenRetry = 1000;


    /**
     * 重试机制重试次数
     */
    private Integer maxRetries = 3;


    /**
     * 命名空间(父节点名称)
     */
    private String namespace = "dbf";


    /**
     * 授权控制
     */
    private String digest;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(Integer connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public Integer getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(Integer sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    public Integer getSleepMsBetweenRetry() {
        return sleepMsBetweenRetry;
    }

    public void setSleepMsBetweenRetry(Integer sleepMsBetweenRetry) {
        this.sleepMsBetweenRetry = sleepMsBetweenRetry;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }



}
