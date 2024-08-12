package io.github.kamarias.dbf.event;

import io.github.kamarias.dbf.timer.TimerTaskInfo;

public class TimerReportTaskEvent extends DbfTimerTaskEvent<String>{


    public TimerReportTaskEvent(TimerTaskInfo<String> timerTaskInfo) {
        super(timerTaskInfo);
    }
}
