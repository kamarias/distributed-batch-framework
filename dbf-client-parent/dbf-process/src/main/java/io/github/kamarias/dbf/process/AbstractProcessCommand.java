package io.github.kamarias.dbf.process;

import java.util.Map;

public abstract class AbstractProcessCommand implements ProcessCommand{
    @Override
    public void start(Map<String, Object> commandMap) {


        this.preProcessor(null);
        this.startProcessor(null);
        this.postProcessor(null);

    }

    @Override
    public void preProcessor(ExecuteParam execute) {
        System.out.println("preProcessor");
    }

    @Override
    public void startProcessor(ExecuteParam execute) {
        System.out.println("startProcessor");
    }

    @Override
    public void postProcessor(ExecuteParam execute) {
        System.out.println("postProcessor");
    }

}
