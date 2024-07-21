package io.github.kamarias.dbf.timer;

import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class HoursTimerTask implements TimerTask {

    private static final List<TimerTaskInfo<?>> taskInfoList = new CopyOnWriteArrayList<>();

    private volatile static HoursTimerTask instance;

    private HoursTimerTask() {
    }

    @Override
    public void run(Timeout timeout) {
        LocalDateTime plusOneHours = LocalDateTime.now().plusHours(1);
        for (TimerTaskInfo<?> taskInfo : taskInfoList) {
            if (plusOneHours.isAfter(taskInfo.getStartDateTime()) || plusOneHours.isEqual(taskInfo.getStartDateTime())) {
                MinutesTimerTask.getTaskInfoList().add(taskInfo);
                taskInfoList.remove(taskInfo);
            }
        }
        timeout.timer().newTimeout(this, 1, TimeUnit.HOURS);
    }

    public static List<TimerTaskInfo<?>> getTaskInfoList() {
        return taskInfoList;
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
