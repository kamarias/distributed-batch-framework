package io.github.kamarias.dbf.app;

import io.github.kamarias.dbf.beans.ReportTaskInfo;
import io.github.kamarias.dbf.event.ReportTaskEvent;
import io.github.kamarias.dbf.event.TimerReportTaskEvent;
import io.github.kamarias.dbf.process.Command;
import io.github.kamarias.dbf.annotation.EnableProcessor;
import io.github.kamarias.dbf.timer.DbfHashedWheelTimer;
import io.github.kamarias.dbf.timer.TimerTaskInfo;
import io.github.kamarias.dbf.timer.TimerTaskUtils;
import io.github.kamarias.dbf.utils.DbfEventUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableProcessor
public class App {
    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);

        DbfHashedWheelTimer bean = context.getBean(DbfHashedWheelTimer.class);
        bean.start();

        Command testProcess1 = context.getBean("testProcess1", Command.class);
        testProcess1.start(new HashMap<>());

        TestProcess12 testProcess12 = context.getBean("testProcess12", TestProcess12.class);
        testProcess1.start(new HashMap<>());

        TestProcess12 testProcess13 = context.getBean("testProcess12", TestProcess12.class);
        testProcess1.start(new HashMap<>());


        ReportTaskInfo taskInfo = new ReportTaskInfo();
        taskInfo.setTaskId("123456");
        taskInfo.setFinishTotal(1);
        taskInfo.setTotal(10);
        taskInfo.setTime(10);


        TimerTaskInfo.TimerTaskInfoBuilder<ReportTaskInfo> builder = TimerTaskInfo.builder();
        TimerTaskInfo<ReportTaskInfo> sdsddd = builder
                .params(taskInfo)
                .source(new Object())
                .taskName("sdfghjk")
                .startDateTime(LocalDateTime.now().plusSeconds(10))
                .build();
        TimerTaskUtils.addTimerTask(new ReportTaskEvent(sdsddd));
        System.in.read();
    }
}
