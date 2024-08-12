package io.github.kamarias.dbf.zk;

import io.github.kamarias.dbf.event.DbfOffLeaderEvent;
import io.github.kamarias.dbf.event.DbfOnLeaderEvent;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.springframework.context.ApplicationContext;

public class DbfLeaderListener implements LeaderLatchListener {

    private final ApplicationContext context;

    public DbfLeaderListener(ApplicationContext publisher) {
        this.context = publisher;
    }

    @Override
    public void isLeader() {
        context.publishEvent(new DbfOnLeaderEvent(this));
    }

    @Override
    public void notLeader() {
        context.publishEvent(new DbfOffLeaderEvent(this));
    }
}
