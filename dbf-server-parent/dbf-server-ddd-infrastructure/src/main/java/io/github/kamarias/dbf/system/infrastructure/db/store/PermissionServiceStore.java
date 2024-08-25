package io.github.kamarias.dbf.system.infrastructure.db.store;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.dbf.system.entity.PermissionEntity;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceStore extends ServiceImpl<PermissionMapper, PermissionEntity> {


}
