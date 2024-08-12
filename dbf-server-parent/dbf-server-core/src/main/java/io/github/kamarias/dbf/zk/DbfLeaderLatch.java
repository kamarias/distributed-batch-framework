package io.github.kamarias.dbf.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * DBF 主节点选择处理器
 */
public class DbfLeaderLatch extends LeaderLatch {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbfLeaderLatch.class);

    /**
     * 类加载时实例化Id
     */
    public static final String INSTANCE_ID = UUID.randomUUID().toString();


    public DbfLeaderLatch(CuratorFramework client) {
        super(client, DbfConstantEnum.LEADER.getValue(), INSTANCE_ID, LeaderLatch.CloseMode.NOTIFY_LEADER);
    }

    /**
     * 判断是否是 leader
     *
     * @return 布尔值
     */
    public boolean isLeader() {
        return this.hasLeadership();
    }

}
