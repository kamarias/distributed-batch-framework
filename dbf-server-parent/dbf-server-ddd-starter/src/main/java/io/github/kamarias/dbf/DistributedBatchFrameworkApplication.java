package io.github.kamarias.dbf;

import io.github.kamarias.dbf.timer.DbfHashedWheelTimer;
import io.github.kamarias.dbf.timer.TimerTaskInfo;
import io.github.kamarias.dbf.timer.TimerTaskType;
import io.github.kamarias.dbf.timer.TimerTaskUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;

@SpringBootApplication(scanBasePackages = {"io.github.kamarias.dbf", "io.github.kamarias.dbf.gateway"})
public class DistributedBatchFrameworkApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.
                        run(DistributedBatchFrameworkApplication.class,
                                args);
        TimerTaskUtils timerTaskUtils = context.getBean(TimerTaskUtils.class);
        DbfHashedWheelTimer dbfHashedWheelTimer = context.getBean(DbfHashedWheelTimer.class);
        dbfHashedWheelTimer.start();

        timerTaskUtils.addTimerTask(new TimerTaskInfo<>("秒级任务3", LocalDateTime.now().plusSeconds(1), TimerTaskType.SATURDAY, null));


    }


}
