package io.github.kamarias.dbf.zk;

import com.sun.javafx.font.PrismFontFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;

import java.util.concurrent.atomic.AtomicBoolean;

public class DbfExecuteListener implements TreeCacheListener {

    private final AtomicBoolean isLeader = new AtomicBoolean(false);

    private final DbfLeaderLatch dbfLeaderLatch;

    public DbfExecuteListener(DbfLeaderLatch dbfLeaderLatch) {
        this.dbfLeaderLatch = dbfLeaderLatch;
    }

    public void onLeader() {
        isLeader.set(true);
    }

    public void offLeader() {
        isLeader.set(false);
    }

    @Override
    public void childEvent(CuratorFramework c, TreeCacheEvent e) {
        if (dbfLeaderLatch.isLeader()) {
            System.out.println("领导者");
            switch (e.getType()) {
                case NODE_ADDED:
                    System.out.println("NODE_ADDED: " + e.getData() + "   ===" + e.getData().getPath());
                    break;
                case NODE_UPDATED:
                    System.out.println("NODE_UPDATED: " + e.getData() + "   ===" + e.getData().getPath());
                    break;
                case NODE_REMOVED:
                    System.out.println("NODE_REMOVED: " + e.getData() + "   ===" + e.getData().getPath());
                    break;
                default:
                    System.out.println("Unknown event type: " + e.getType());
            }
        }
    }

}
