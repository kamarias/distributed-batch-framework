package io.github.kamarias.dbf.timer;

import io.github.kamarias.dbf.event.DbfTimerTaskEvent;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class HoursTimerTask implements TimerTask {

    private static final List<DbfTimerTaskEvent<?>> taskInfoList = new CopyOnWriteArrayList<>();

    private volatile static HoursTimerTask instance;

    private HoursTimerTask() {
    }

    @Override
    public void run(Timeout timeout) {
        LocalDateTime plusOneHours = LocalDateTime.now().plusHours(1);
        for (DbfTimerTaskEvent<?> taskInfo : taskInfoList) {
            if (plusOneHours.isAfter(taskInfo.getStartDateTime()) || plusOneHours.isEqual(taskInfo.getStartDateTime())) {
                MinutesTimerTask.add(taskInfo);
                taskInfoList.remove(taskInfo);
            }
        }
        timeout.timer().newTimeout(this, 1, TimeUnit.HOURS);
    }

    public static <E extends DbfTimerTaskEvent<?>> boolean add(E event) {
        return taskInfoList.add(event);
    }


    public static <E extends DbfTimerTaskEvent<?>> boolean remove(E event) {
        return taskInfoList.remove(event);
    }

    public static HoursTimerTask getInstance() {
        if (instance == null) {
            synchronized (HoursTimerTask.class) {
                if (instance == null) {
                    instance = new HoursTimerTask();
                }
            }
        }
        return instance;
    }


}
