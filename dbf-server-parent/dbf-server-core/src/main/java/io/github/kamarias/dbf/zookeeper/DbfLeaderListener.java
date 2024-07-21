package io.github.kamarias.dbf.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;


public class DbfLeaderListener implements LeaderLatchListener {

    private final CuratorFramework client;

    public DbfLeaderListener(CuratorFramework client) {
        this.client = client;
    }

    @Override
    public void isLeader() {

    }

    @Override
    public void notLeader() {

    }

}
