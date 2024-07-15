package io.github.kamarias.dbf.process;


import java.util.Map;

public interface Command {


    void start(Map<String,Object> commandMap);


}
