package io.github.kamarias.dbf.timer;

import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class SecondTimerTask implements TimerTask {

    private static final List<TimerTaskInfo<?>> taskInfoList = new CopyOnWriteArrayList<>();

    private volatile static SecondTimerTask instance;

    private SecondTimerTask() {
    }

    @Override
    public void run(Timeout timeout) {
        LocalDateTime now = LocalDateTime.now();
        for (TimerTaskInfo<?> taskInfo : taskInfoList) {
            if (now.isAfter(taskInfo.getStartDateTime()) || now.isEqual(taskInfo.getStartDateTime())) {
                process(taskInfo);
                taskInfoList.remove(taskInfo);
            }
        }

        timeout.timer().newTimeout(this, 1, TimeUnit.SECONDS);
    }

    private void process(TimerTaskInfo<?> taskInfo) {
        SpringApplicationContextUtils.applicationContext
                .publishEvent(new TimerTaskApplicationEvent(SpringApplicationContextUtils.applicationContext, taskInfo));
    }


    public static List<TimerTaskInfo<?>> getTaskInfoList() {
        return taskInfoList;
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
