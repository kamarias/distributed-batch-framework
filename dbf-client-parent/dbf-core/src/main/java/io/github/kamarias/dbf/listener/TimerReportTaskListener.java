package io.github.kamarias.dbf.listener;

import io.github.kamarias.dbf.beans.ReportTaskInfo;
import io.github.kamarias.dbf.event.ReportTaskEvent;
import io.github.kamarias.dbf.event.TimerReportTaskEvent;
import io.github.kamarias.dbf.timer.TimerTaskInfo;
import io.github.kamarias.dbf.timer.TimerTaskUtils;
import io.github.kamarias.dbf.utils.DbfEventUtils;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public class TimerReportTaskListener implements DbfListener<TimerReportTaskEvent> {

    private int i = 1;

    @Override
    public void onApplicationEvent(@NonNull TimerReportTaskEvent event) {

        ReportTaskInfo taskInfo = new ReportTaskInfo();
        taskInfo.setTaskId("123456");
        taskInfo.setFinishTotal(i++);
        taskInfo.setTotal(10);
        taskInfo.setTime(10);

        TimerTaskInfo.TimerTaskInfoBuilder<ReportTaskInfo> builder = TimerTaskInfo.builder();
        TimerTaskInfo<ReportTaskInfo> sdsddd = builder
                .params(taskInfo)
                .source(new Object())
                .taskName(event.getParams() + "~")
                .build();
        // 使用这个定时参数必传
//      TimerTaskUtils.addTimerTask(new ReportTaskEvent(sdsddd));
        // 使用这个可以不定时
        DbfEventUtils.publishEvent(new ReportTaskEvent(sdsddd));
    }
}
