package io.github.kamarias.dbf.listener;

import io.github.kamarias.dbf.timer.SecondTimerTask;
import io.github.kamarias.dbf.timer.TimerTaskApplicationEvent;
import io.github.kamarias.dbf.timer.TimerTaskInfo;
import io.github.kamarias.dbf.timer.TimerTaskType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimerTaskEventListener implements ApplicationListener<TimerTaskApplicationEvent> {


    private final TaskExecutor executor;


    public TimerTaskEventListener(@Qualifier("timerTaskThreadPool") TaskExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void onApplicationEvent(TimerTaskApplicationEvent event) {
        executor.execute(()->{
            invokeTimerTask(event);
        });
    }

    private void invokeTimerTask(TimerTaskApplicationEvent event) {
        ApplicationContext context = (ApplicationContext) event.getSource();
        TimerTaskInfo<?> timerTaskInfo = event.getTimerTaskInfo();
        switch (timerTaskInfo.getTaskType()) {
            case APPLICATION_TASK:
                System.out.println("Application started" + Thread.currentThread().getName());
                break;
            case SATURDAY:
                System.out.println("Saturday started" + Thread.currentThread().getName());
                SecondTimerTask.getTaskInfoList().add(new TimerTaskInfo<>("秒级任务3", LocalDateTime.now().plusSeconds(1), TimerTaskType.SATURDAY, null));
                break;
            case HEALTH_MONITOR:
                System.out.println("Friday started" + Thread.currentThread().getName());
                break;
            default:
                System.out.println("Unknown task type" + Thread.currentThread().getName());
        }
    }
}
