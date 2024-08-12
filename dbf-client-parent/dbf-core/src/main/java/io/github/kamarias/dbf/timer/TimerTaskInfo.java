package io.github.kamarias.dbf.timer;

import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public class TimerTaskInfo<P> {

    @NonNull
    private final Object source;

    @NonNull
    private final String taskName;

    @NonNull
    private final LocalDateTime startDateTime;

    @NonNull
    private final P params;

    private TimerTaskInfo(TimerTaskInfoBuilder<P> builder) {
        this.source = builder.source;
        this.taskName = builder.taskName;
        this.startDateTime = builder.startDateTime;
        this.params = builder.params;
    }

    // 静态方法，用于开始构建过程
    public static <P> TimerTaskInfoBuilder<P> builder() {
        return new TimerTaskInfoBuilder<>();
    }

    // 内部类 TimerTaskInfoBuilder
    public static class TimerTaskInfoBuilder<P> {

        private Object source;
        private String taskName;
        private LocalDateTime startDateTime;
        private P params;

        // 私有构造方法
        private TimerTaskInfoBuilder() {
        }

        public TimerTaskInfoBuilder<P> source(@NonNull Object source) {
            this.source = source;
            return this;
        }

        public TimerTaskInfoBuilder<P> taskName(@NonNull String taskName) {
            this.taskName = taskName;
            return this;
        }

        public TimerTaskInfoBuilder<P> startDateTime(@NonNull LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
            return this;
        }

        public TimerTaskInfoBuilder<P> params(@NonNull P params) {
            this.params = params;
            return this;
        }

        public TimerTaskInfo<P> build() {
            return new TimerTaskInfo<>(this);
        }
    }

    @NonNull
    public Object getSource() {
        return source;
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
