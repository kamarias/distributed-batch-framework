package io.github.kamarias.dbf.zk;

import io.github.kamarias.dbf.event.DbfExecuteAddEvent;
import io.github.kamarias.dbf.event.DbfExecuteDelEvent;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class DbfExecuteRegisterListener implements TreeCacheListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbfExecuteRegisterListener.class);

    private final ApplicationContext context;


    public DbfExecuteRegisterListener(ApplicationContext context) {
        this.context = context;
    }

    /**
     * 执行
     * 监听注册执行器注册节点 （/register/appName/groupId/executeId）
     *
     * @param client the client zk 客户端
     * @param event  describes the change 变更事件
     */
    @Override
    public void childEvent(CuratorFramework client, TreeCacheEvent event) {
        // 对于执行器注册，只监听注册、卸载事件, 5是执行器的树深度
        switch (event.getType()) {
            case NODE_ADDED:
                if (event.getData().getPath().split("/").length == 5) {
                    context.publishEvent(new DbfExecuteAddEvent(this, client, event));
                }
                break;
            case NODE_REMOVED:
                if (event.getData().getPath().split("/").length == 5) {
                    context.publishEvent(new DbfExecuteDelEvent(this, client, event));
                }
                break;
            default:
                break;
        }
    }

    public ApplicationContext getContext() {
        return context;
    }

}
