package io.github.kamarias.dbf.domain;

import io.github.kamarias.dbf.gateway.TaskGroupGateway;
import org.springframework.stereotype.Component;

@Component
public class TaskService {

    private final TaskGroupGateway taskGroupGateway;


    public TaskService( TaskGroupGateway taskGroupGateway) {
        this.taskGroupGateway = taskGroupGateway;
    }

    public void test(){
        taskGroupGateway.test();
    }

}
