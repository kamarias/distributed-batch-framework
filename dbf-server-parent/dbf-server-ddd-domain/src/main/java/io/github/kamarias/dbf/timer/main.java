package io.github.kamarias.dbf.timer;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class main {

    public static void main(String[] args) {
        HoursTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("小时任务1", LocalDateTime.now().plusMinutes(4), null, null));
        HoursTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("小时任务2", LocalDateTime.now().plusMinutes(5), null, null));
        MinutesTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("分钟任务1", LocalDateTime.now().plusMinutes(1), null, null));
        MinutesTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("分钟任务2", LocalDateTime.now().plusMinutes(2), null, null));
        MinutesTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("分钟任务3", LocalDateTime.now().plusMinutes(3), null, null));
        SecondTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("秒级任务1", LocalDateTime.now().plusSeconds(5), null, null));
        SecondTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("秒级任务2", LocalDateTime.now().plusSeconds(10), null, null));
        SecondTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("秒级任务3", LocalDateTime.now().plusSeconds(15), null, null));

        DbfHashedWheelTimer dbfHashedWheelTimer = new DbfHashedWheelTimer();
        dbfHashedWheelTimer.start();

        while (true){
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){

            }
        }

    }

}
