package io.github.kamarias.dbf.zookeeper;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@Import(DbfZookeeperProperties.class)
public class DbfZookeeperConfigurer {

    private final DbfZookeeperProperties properties;

    private final DbfClusterInstance instance;


    public DbfZookeeperConfigurer(DbfZookeeperProperties dbfZookeeperProperties, DbfClusterInstance instance) {
        this.properties = dbfZookeeperProperties;
        this.instance = instance;
    }

    @Bean("curatorClient")
    public CuratorFramework curatorClient() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                //连接地址  集群用,隔开
                .connectString(properties.getAddress())
                .connectionTimeoutMs(properties.getConnectionTimeoutMs())
                //会话超时时间
                .sessionTimeoutMs(properties.getSessionTimeOut())
                //设置重试机制
                .retryPolicy(new ExponentialBackoffRetry(properties.getSleepMsBetweenRetry(), properties.getMaxRetries()))
                //设置命名空间 在操作节点的时候，会以这个为父节点
                .namespace(properties.getNamespace())
                .build();
        client.start();

        // Register the listener instance
        LeaderLatch leaderLatch = new LeaderLatch(client, DbfZookeeperEnum.LEADER_PATH.getPath(), instance.getInstanceId(), LeaderLatch.CloseMode.NOTIFY_LEADER);
        leaderLatch.addListener(new DbfLeaderListener(client));
        leaderLatch.start();
        return client;
    }

}
