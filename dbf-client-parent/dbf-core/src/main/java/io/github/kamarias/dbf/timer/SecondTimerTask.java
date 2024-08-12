package io.github.kamarias.dbf.timer;

import io.github.kamarias.dbf.event.DbfTimerTaskEvent;
import io.github.kamarias.dbf.utils.DbfEventUtils;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;


public class SecondTimerTask implements TimerTask {

    private static final List<DbfTimerTaskEvent<?>> taskInfoList = new CopyOnWriteArrayList<>();

    private volatile static SecondTimerTask instance;

    private SecondTimerTask() {
    }

    @Override
    public void run(Timeout timeout) {
        LocalDateTime now = LocalDateTime.now();
        for (DbfTimerTaskEvent<?> taskInfo : taskInfoList) {
            if (now.isAfter(taskInfo.getStartDateTime()) || now.isEqual(taskInfo.getStartDateTime())) {
                DbfEventUtils.publishEvent(taskInfo);
                SecondTimerTask.remove(taskInfo);
            }
        }
        timeout.timer().newTimeout(this, 1, TimeUnit.SECONDS);
    }

    public static <E extends DbfTimerTaskEvent<?>> boolean add(E event) {
        return taskInfoList.add(event);
    }


    public static <E extends DbfTimerTaskEvent<?>> boolean remove(E event) {
        return taskInfoList.remove(event);
    }


    public static SecondTimerTask getInstance() {
        if (instance == null) {
            synchronized (SecondTimerTask.class) {
                if (instance == null) {
                    instance = new SecondTimerTask();
                }
            }
        }
        return instance;
    }


}
