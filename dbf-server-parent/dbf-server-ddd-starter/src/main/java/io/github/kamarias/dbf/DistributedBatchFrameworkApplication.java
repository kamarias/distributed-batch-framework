package io.github.kamarias.dbf;

import io.github.kamarias.dbf.timer.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;

@SpringBootApplication
public class DistributedBatchFrameworkApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.
                        run(DistributedBatchFrameworkApplication.class,
                                args);


        HoursTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("小时任务1", LocalDateTime.now().plusMinutes(4), TimerTaskType.APPLICATION_TASK, null));
        HoursTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("小时任务2", LocalDateTime.now().plusMinutes(5), TimerTaskType.APPLICATION_TASK, null));
        MinutesTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("分钟任务1", LocalDateTime.now().plusMinutes(1), null, null));
        MinutesTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("分钟任务2", LocalDateTime.now().plusMinutes(2), TimerTaskType.APPLICATION_TASK, null));
        MinutesTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("分钟任务3", LocalDateTime.now().plusMinutes(3), TimerTaskType.APPLICATION_TASK, null));
        SecondTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("秒级任务1", LocalDateTime.now().plusSeconds(5), TimerTaskType.APPLICATION_TASK, null));
        SecondTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("秒级任务2", LocalDateTime.now().plusSeconds(10), TimerTaskType.SATURDAY, null));
        SecondTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("秒级任务3", LocalDateTime.now().plusSeconds(15), TimerTaskType.SATURDAY, null));

        DbfHashedWheelTimer dbfHashedWheelTimer = new DbfHashedWheelTimer();
        dbfHashedWheelTimer.start();

    }


}
