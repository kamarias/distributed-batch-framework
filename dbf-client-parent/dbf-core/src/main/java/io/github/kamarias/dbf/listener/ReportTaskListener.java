package io.github.kamarias.dbf.listener;

import io.github.kamarias.dbf.beans.ReportTaskInfo;
import io.github.kamarias.dbf.event.ReportTaskEvent;
import io.github.kamarias.dbf.event.TimerReportTaskEvent;
import io.github.kamarias.dbf.timer.TimerTaskInfo;
import io.github.kamarias.dbf.timer.TimerTaskUtils;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public class ReportTaskListener implements DbfListener<ReportTaskEvent> {


    @Override
    public void onApplicationEvent(@NonNull ReportTaskEvent event) {
        ReportTaskInfo params = event.getParams();
        System.out.println("当前已完成数：" + params.getFinishTotal());

        if (params.getFinishTotal() < params.getTotal()) {
            TimerTaskInfo.TimerTaskInfoBuilder<String> builder = TimerTaskInfo.builder();
            TimerTaskInfo<String> sdsddd = builder
                    .params("sdsddd")
                    .source(this)
                    .taskName("sdfghjk")
                    .startDateTime(LocalDateTime.now().plusSeconds(params.getTime()))
                    .build();
            TimerTaskUtils.addTimerTask(new TimerReportTaskEvent(sdsddd));
        }
    }


}
