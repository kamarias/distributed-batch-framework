package io.github.kamarias.dbf.listener;

import io.github.kamarias.dbf.event.DbfOnLeaderEvent;
import io.github.kamarias.dbf.zk.DbfExecuteTreeCache;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DbfOnLeaderExecuteListener implements ApplicationListener<DbfOnLeaderEvent> {

    private final DbfExecuteTreeCache dbfExecuteTreeCache;

    public DbfOnLeaderExecuteListener(DbfExecuteTreeCache dbfExecuteTreeCache) {
        this.dbfExecuteTreeCache = dbfExecuteTreeCache;
    }


    @Override
    public void onApplicationEvent(DbfOnLeaderEvent event) {
        try {
            dbfExecuteTreeCache.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
