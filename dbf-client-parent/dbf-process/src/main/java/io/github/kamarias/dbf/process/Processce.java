package io.github.kamarias.dbf.process;


public interface Processce {

    /**
     * 进程前置处理
     */
    void preProcessor(ExecuteParam execute);


    /**
     * 开始处理进程
     */
    void startProcessor(ExecuteParam execute);


    /**
     * 进程处理完成后
     */
    void postProcessor(ExecuteParam execute);

}
