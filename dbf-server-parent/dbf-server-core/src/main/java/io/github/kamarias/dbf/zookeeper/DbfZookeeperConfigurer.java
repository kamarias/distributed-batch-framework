package io.github.kamarias.dbf.zookeeper;


import io.github.kamarias.dbf.timer.TimerTaskUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PreDestroy;

@Configuration(proxyBeanMethods = false)
@Import({DbfZookeeperProperties.class, DbfConfigConfigurationRegistrar.class})
public class DbfZookeeperConfigurer {

    private CuratorFramework client;
    private LeaderLatch leaderLatch;
    private PathChildrenCache childrenCache;

    private final DbfApplicationContext applicationContext;

    public DbfZookeeperConfigurer(DbfApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean("curatorClient")
    public CuratorFramework curatorClient(DbfZookeeperProperties properties) {
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

        // 注册上下文
        boolean registered = DbfClusterInstance.registerClusterMap(client, applicationContext);
        if (!registered) {
            throw new RuntimeException("registered Cluster Map Exception");
        }
        this.client = client;
        return client;
    }

    @Bean
    public LeaderLatch leaderLatch(CuratorFramework client, TimerTaskUtils timerTaskUtils) throws Exception {
        LeaderLatch leaderLatch = new LeaderLatch(client, DbfZookeeperEnum.LEADER_PATH.getValue(), DbfClusterInstance.getInstanceId());
        leaderLatch.addListener(new DbfLeaderListener(client, timerTaskUtils)); // 假设timerTaskUtils是一个已配置的bean
        leaderLatch.start();
        this.leaderLatch = leaderLatch;
        return leaderLatch;
    }

    @Bean
    public PathChildrenCache pathChildrenCache(CuratorFramework client) throws Exception {
        PathChildrenCache cache = new PathChildrenCache(client, DbfZookeeperEnum.INSTANCE_DATE.getValue(), true);
        cache.getListenable().addListener(DbfClusterInstance::refreshLocalClusterMap);
        this.childrenCache = cache;
        cache.start();
        return cache;
    }

    @PreDestroy
    public void destroyResources() throws Exception {
        if (childrenCache != null) {
            childrenCache.close();
        }
        if (leaderLatch != null) {
            leaderLatch.close();
        }
        if (client != null) {
            client.close();
        }
    }

}
