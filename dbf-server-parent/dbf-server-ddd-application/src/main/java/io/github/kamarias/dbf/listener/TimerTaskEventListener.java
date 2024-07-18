package io.github.kamarias.dbf.listener;

import io.github.kamarias.dbf.domain.TaskService;
import io.github.kamarias.dbf.timer.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimerTaskEventListener implements ApplicationListener<TimerTaskApplicationEvent> {


    private final TaskExecutor executor;


    private final TaskService taskService;

    private final TimerTaskUtils timerTaskUtils;

    public TimerTaskEventListener(@Qualifier("timerTaskThreadPool") TaskExecutor executor, TaskService taskService, TimerTaskUtils timerTaskUtils) {
        this.executor = executor;
        this.taskService = taskService;
        this.timerTaskUtils = timerTaskUtils;
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
                System.out.println("Application started" + timerTaskInfo  + "   " + LocalDateTime.now());
                break;
            case SATURDAY:
                System.out.println("Saturday started" + timerTaskInfo+ "   " + LocalDateTime.now());
                taskService.test();
                timerTaskUtils.addTimerTask(new TimerTaskInfo<>("秒级任务3", LocalDateTime.now().plusSeconds(1), TimerTaskType.SATURDAY, null));
                break;
            case HEALTH_MONITOR:
                System.out.println("Friday started" + timerTaskInfo+ "   " + LocalDateTime.now());
                break;
            default:
                System.out.println("Unknown task type" + timerTaskInfo+ "   " + LocalDateTime.now());
        }
    }
}
