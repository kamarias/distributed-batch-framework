package io.github.kamarias.dbf.timer;

import java.time.LocalDateTime;
import java.util.Objects;

public class TimerTaskInfo<P> {


    /**
     * 任务名称
     */
    private final String taskName;


    /**
     * 启动时间
     */
    private final LocalDateTime startDateTime;

    /**
     * 任务类型
     */
    private final TimerTaskType taskType;

    /**
     * 请求参数
     */
    private final P params;

    public TimerTaskInfo(String taskName, LocalDateTime startDateTime, TimerTaskType taskType, P params) {
        this.taskName = taskName;
        this.startDateTime = startDateTime;
        this.taskType = Objects.isNull(taskType) ? TimerTaskType.NONE : taskType;
        this.params = params;
    }

    public String getTaskName() {
        return taskName;
    }


    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }


    public TimerTaskType getTaskType() {
        return taskType;
    }


    public P getParams() {
        return params;
    }


    @Override
    public String toString() {
        return "TimerTaskInfo{" +
                "taskType='" + taskType + '\'' +
                ", startDateTime=" + startDateTime +
                ", taskName='" + taskName + '\'' +
                ", params=" + params +
                '}';
    }
}
