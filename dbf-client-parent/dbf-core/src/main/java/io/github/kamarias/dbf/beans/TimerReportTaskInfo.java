package io.github.kamarias.dbf.beans;

import java.io.Serializable;

public class TimerReportTaskInfo implements Serializable {


    /**
     * 任务Id
     */
    private String taskId;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

}
