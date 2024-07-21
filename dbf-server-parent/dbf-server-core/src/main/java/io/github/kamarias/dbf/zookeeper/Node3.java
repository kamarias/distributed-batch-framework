package io.github.kamarias.dbf.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Node3 {


    private static final String CONNECT_STRING = "192.168.43.10:2181"; // ZooKeeper连接字符串
    private static final String LEADER_PATH = "/leader_latch"; // 领导选举路径
    private static final String NODE_ID = "node3"; // 节点ID

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                CONNECT_STRING,
                new ExponentialBackoffRetry(1000, 3)
        );
        client.start();



        LeaderLatch leaderLatch = new LeaderLatch(client, LEADER_PATH, NODE_ID, LeaderLatch.CloseMode.NOTIFY_LEADER);
        leaderLatch.addListener(new LeaderLatchListener() {
            @Override
            public void isLeader() {
                System.out.println(NODE_ID + " is the leader!");
//                LeaderUtil.setLeader(NODE_ID); // 设置当前领导者
                // 在这里执行领导者的任务
            }

            @Override
            public void notLeader() {
                System.out.println(NODE_ID + " is not the leader.");
                // 在这里执行非领导者的任务（可选）
            }
        });

        leaderLatch.start();



        // 模拟节点运行，等待用户中断
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                leaderLatch.close();
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        System.out.println(leaderLatch);
        // 保持程序运行，直到用户中断
        Thread.sleep(Long.MAX_VALUE);
    }
}
