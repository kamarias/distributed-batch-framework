package io.github.kamarias.dbf.listener;

import io.github.kamarias.dbf.event.DbfOffLeaderEvent;
import io.github.kamarias.dbf.zk.DbfExecuteTreeCache;
import io.github.kamarias.dbf.zk.DbfRegisterTreeCache;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class DbfOffLeaderExecuteListener implements ApplicationListener<DbfOffLeaderEvent> {

    private final DbfExecuteTreeCache dbfExecuteTreeCache;

    private final DbfRegisterTreeCache dbfRegisterTreeCache;

    public DbfOffLeaderExecuteListener(DbfExecuteTreeCache dbfExecuteTreeCache, DbfRegisterTreeCache dbfRegisterTreeCache) {
        this.dbfExecuteTreeCache = dbfExecuteTreeCache;
        this.dbfRegisterTreeCache = dbfRegisterTreeCache;
    }


    @Override
    public void onApplicationEvent(DbfOffLeaderEvent event) {
        dbfExecuteTreeCache.close();
        dbfRegisterTreeCache.close();
    }
}
