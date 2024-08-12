package io.github.kamarias.dbf.zk;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@Import({DbfZookeeperProperties.class})
public class DbfZookeeperConfigurer {


    @Bean(value = "curatorClient", initMethod = "start")
    public CuratorFramework curatorClient(DbfZookeeperProperties properties) {
        return CuratorFrameworkFactory.builder()
                //连接地址  集群用,隔开
                .connectString(properties.getAddress()).connectionTimeoutMs(properties.getConnectionTimeoutMs())
                //会话超时时间
                .sessionTimeoutMs(properties.getSessionTimeOut())
                //设置重试机制
                .retryPolicy(new ExponentialBackoffRetry(properties.getSleepMsBetweenRetry(), properties.getMaxRetries()))
                //设置命名空间 在操作节点的时候，会以这个为父节点
                .namespace(properties.getNamespace()).build();
    }


    @Bean
    public DbfLeaderListener dbfLeaderListener(ApplicationContext context) {
        return new DbfLeaderListener(context);
    }

    @Bean
    public DbfExecuteListener dbfExecuteListener(DbfLeaderLatch dbfLeaderLatch) {
        return new DbfExecuteListener(dbfLeaderLatch);
    }


    @Bean
    public DbfExecuteTreeCache dbfTreeCache(CuratorFramework client, DbfExecuteListener dbfExecuteListener) {
        DbfExecuteTreeCache dbfExecuteTreeCache = new DbfExecuteTreeCache(client, DbfConstantEnum.EXECUTE.getValue());
        dbfExecuteTreeCache.getListenable().addListener(dbfExecuteListener);
        return dbfExecuteTreeCache;
    }

    @Bean(initMethod = "start")
    public DbfLeaderLatch leaderLatch(CuratorFramework client, DbfLeaderListener dbfLeaderListener) {
        DbfLeaderLatch dbfLeaderLatch = new DbfLeaderLatch(client);
        dbfLeaderLatch.addListener(dbfLeaderListener);
        return dbfLeaderLatch;
    }

}
