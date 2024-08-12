package io.github.kamarias.dbf.event;

import io.github.kamarias.dbf.beans.ReportTaskInfo;
import io.github.kamarias.dbf.timer.TimerTaskInfo;

/**
 * 任务上报进度事件
 */
public class ReportTaskEvent extends DbfTimerTaskEvent<ReportTaskInfo> {


    public ReportTaskEvent(TimerTaskInfo<ReportTaskInfo> timerTaskInfo) {
        super(timerTaskInfo);
    }


}
