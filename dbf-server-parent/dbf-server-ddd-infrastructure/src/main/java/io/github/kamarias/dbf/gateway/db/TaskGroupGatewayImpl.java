package io.github.kamarias.dbf.gateway.db;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.kamarias.dbf.gateway.TaskGroupGateway;
import io.github.kamarias.dbf.infrastructure.db.mapper.TaskGroupMapper;
import io.github.kamarias.dbf.infrastructure.db.service.TaskGroupService;
import org.springframework.stereotype.Service;

@Service
public class TaskGroupGatewayImpl implements TaskGroupGateway {

    private final TaskGroupService taskGroupService;

    private final TaskGroupMapper taskGroupMapper;

    public TaskGroupGatewayImpl(TaskGroupService taskGroupService, TaskGroupMapper taskGroupMapper) {
        this.taskGroupService = taskGroupService;
        this.taskGroupMapper = taskGroupMapper;
    }


    @Override
    public void test() {
        System.out.println("rt");
        System.out.println(taskGroupService.count());
        System.out.println(taskGroupMapper.selectCount(new QueryWrapper<>()));
    }
}
