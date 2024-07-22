package io.github.kamarias.dbf.zookeeper;

import io.github.kamarias.dbf.timer.TimerTaskInfo;
import io.github.kamarias.dbf.timer.TimerTaskType;
import io.github.kamarias.dbf.timer.TimerTaskUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;


public class DbfLeaderListener implements LeaderLatchListener {

    private static final Logger logger = LoggerFactory.getLogger(DbfLeaderListener.class);


    private final CuratorFramework client;


    /**
     * 框架任务调度
     */
    private final TimerTaskUtils timerTaskUtils;


    public DbfLeaderListener(CuratorFramework client, TimerTaskUtils timerTaskUtils) {
        this.client = client;
        this.timerTaskUtils = timerTaskUtils;
    }

    @Override
    public void isLeader() {
        boolean b = false;
        b = DbfClusterInstance.refreshClusterMapToMaster(client);
        if (!b) {
            // 切换锁失败，发送调度任务告警
            timerTaskUtils.addTimerTask(new TimerTaskInfo<>("DBF 集群领导者切换",
                    LocalDateTime.now(),
                    TimerTaskType.CLUSTER_LEADER,
                    DbfClusterInstance.getInstanceId()));
        }
    }

    @Override
    public void notLeader() {

    }
}
