package io.github.kamarias.dbf.timer;

import io.github.kamarias.dbf.event.DbfTimerTaskEvent;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class MinutesTimerTask implements TimerTask {

    private static final List<DbfTimerTaskEvent<?>> taskInfoList = new CopyOnWriteArrayList<>();


    private volatile static MinutesTimerTask instance;

    private MinutesTimerTask() {
    }

    @Override
    public void run(Timeout timeout) {
        LocalDateTime plusFiveMinutes = LocalDateTime.now().plusMinutes(5);

        for (DbfTimerTaskEvent<?> taskInfo : taskInfoList) {
            if (plusFiveMinutes.isAfter(taskInfo.getStartDateTime()) || plusFiveMinutes.isEqual(taskInfo.getStartDateTime())) {
                SecondTimerTask.add(taskInfo);
                taskInfoList.remove(taskInfo);
            }
        }
        timeout.timer().newTimeout(this, 5, TimeUnit.MINUTES);
    }

    public static <E extends DbfTimerTaskEvent<?>> boolean add(E event) {
        return taskInfoList.add(event);
    }


    public static <E extends DbfTimerTaskEvent<?>> boolean remove(E event) {
        return taskInfoList.remove(event);
    }


    public static MinutesTimerTask getInstance() {
        if (instance == null) {
            synchronized (MinutesTimerTask.class) {
                if (instance == null) {
                    instance = new MinutesTimerTask();
                }
            }
        }
        return instance;
    }
}
