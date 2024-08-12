package io.github.kamarias.dbf.event;

import io.github.kamarias.dbf.timer.TimerTaskInfo;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public abstract class DbfTimerTaskEvent<P> extends DbfEvent {

    @NonNull
    private final String taskName;

    @NonNull
    private final LocalDateTime startDateTime;

    @NonNull
    private final P params;

    public DbfTimerTaskEvent(TimerTaskInfo<P> info) {
        super(info.getSource());
        this.taskName = info.getTaskName();
        this.startDateTime = info.getStartDateTime();
        this.params = info.getParams();
    }

    @NonNull
    public String getTaskName() {
        return taskName;
    }

    @NonNull
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    @NonNull
    public P getParams() {
        return params;
    }
}
