package io.github.kamarias.dbf.beans;

import java.io.Serializable;


/**
 * 上报任务进度信息
 */
public class ReportTaskInfo implements Serializable {


    /**
     * 任务Id
     */
    private String taskId;


    /**
     * 已完成数
     */
    private long finishTotal;

    /**
     * 任务总数
     */
    private long total;

    /**
     * 上报频率，单位秒（默认30秒上报一次作业执行进度）
     */
    private long time = 30;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public long getFinishTotal() {
        return finishTotal;
    }

    public void setFinishTotal(long finishTotal) {
        this.finishTotal = finishTotal;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
