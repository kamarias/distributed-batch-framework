package io.github.kamarias.dbf.timer;

import org.springframework.context.ApplicationEvent;

public class TimerTaskApplicationEvent extends ApplicationEvent {


    /**
     * 定时调度任务
     */
    private final TimerTaskInfo<?> timerTaskInfo;



    public TimerTaskApplicationEvent(Object source, TimerTaskInfo<?> timerTaskInfo) {
        super(source);
        this.timerTaskInfo = timerTaskInfo;
    }

    public TimerTaskInfo<?> getTimerTaskInfo() {
        return timerTaskInfo;
    }

}
