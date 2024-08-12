package io.github.kamarias.dbf.listener;

import io.github.kamarias.dbf.event.DbfOffLeaderEvent;
import io.github.kamarias.dbf.zk.DbfExecuteTreeCache;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class DbfOffLeaderExecuteListener implements ApplicationListener<DbfOffLeaderEvent> {

    private final DbfExecuteTreeCache dbfExecuteTreeCache;

    public DbfOffLeaderExecuteListener(DbfExecuteTreeCache dbfExecuteTreeCache) {
        this.dbfExecuteTreeCache = dbfExecuteTreeCache;
    }


    @Override
    public void onApplicationEvent(DbfOffLeaderEvent event) {
        dbfExecuteTreeCache.close();
    }
}
