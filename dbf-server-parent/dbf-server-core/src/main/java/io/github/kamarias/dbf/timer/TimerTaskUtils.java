package io.github.kamarias.dbf.timer;


import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimerTaskUtils {


    /**
     * 添加调度任务
     *
     * @param timerTaskInfo 定时调度任务
     * @return 返回添加成功的调度任务
     */
    public TimerTaskInfo<?> addTimerTask(TimerTaskInfo<?> timerTaskInfo) {
        // 加一分钟，防止循环时正好卡点导致时间论少一圈
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(1);
        LocalDateTime startDateTime = timerTaskInfo.getStartDateTime();
        if (startDateTime.isAfter(localDateTime.plusHours(1))) {
            HoursTimerTask.getTaskInfoList().add(timerTaskInfo);
        } else if (startDateTime.isAfter(localDateTime.plusMinutes(5))) {
            MinutesTimerTask.getTaskInfoList().add(timerTaskInfo);
        } else {
            SecondTimerTask.getTaskInfoList().add(timerTaskInfo);
        }
        return timerTaskInfo;
    }


}
