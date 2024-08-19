package io.github.kamarias.dbf.event;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.springframework.context.ApplicationEvent;

public class DbfExecuteAddEvent extends ApplicationEvent {

    private final CuratorFramework client;

    private final TreeCacheEvent event;

    public DbfExecuteAddEvent(Object source, CuratorFramework client, TreeCacheEvent event) {
        super(source);
        this.client = client;
        this.event = event;
    }

    public CuratorFramework getClient() {
        return client;
    }

    public TreeCacheEvent getEvent() {
        return event;
    }
}
