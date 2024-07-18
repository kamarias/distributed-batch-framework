package io.github.kamarias.dbf.infrastructure.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.dbf.entity.TaskGroup;
import io.github.kamarias.dbf.infrastructure.db.mapper.TaskGroupMapper;
import io.github.kamarias.dbf.infrastructure.db.service.TaskGroupService;
import org.springframework.stereotype.Service;

@Service
public class TaskGroupServiceImpl extends ServiceImpl<TaskGroupMapper, TaskGroup> implements TaskGroupService {


}
