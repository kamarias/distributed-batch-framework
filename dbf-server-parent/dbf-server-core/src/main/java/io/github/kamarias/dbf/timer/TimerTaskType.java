package io.github.kamarias.dbf.timer;


public enum TimerTaskType {


    /**
     * 注册应用任务调度
     */
    APPLICATION_TASK,

    /**
     * 健康监测
     */
    HEALTH_MONITOR,


    /**
     * 集群领导切换
     */
    CLUSTER_LEADER,


    /**
     * 空处理任务
     */
    NONE;


}
