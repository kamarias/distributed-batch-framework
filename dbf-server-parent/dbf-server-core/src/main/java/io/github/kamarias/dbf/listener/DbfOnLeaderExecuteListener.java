package io.github.kamarias.dbf.listener;

import io.github.kamarias.dbf.event.DbfOnLeaderEvent;
import io.github.kamarias.dbf.zk.DbfExecuteTreeCache;
import io.github.kamarias.dbf.zk.DbfRegisterTreeCache;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DbfOnLeaderExecuteListener implements ApplicationListener<DbfOnLeaderEvent> {

    private final DbfExecuteTreeCache dbfExecuteTreeCache;

    private final DbfRegisterTreeCache dbfRegisterTreeCache;

    public DbfOnLeaderExecuteListener(DbfExecuteTreeCache dbfExecuteTreeCache, DbfRegisterTreeCache dbfRegisterTreeCache) {
        this.dbfExecuteTreeCache = dbfExecuteTreeCache;
        this.dbfRegisterTreeCache = dbfRegisterTreeCache;
    }


    @Override
    public void onApplicationEvent(DbfOnLeaderEvent event) {
        try {
            dbfExecuteTreeCache.start();
            dbfRegisterTreeCache.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
