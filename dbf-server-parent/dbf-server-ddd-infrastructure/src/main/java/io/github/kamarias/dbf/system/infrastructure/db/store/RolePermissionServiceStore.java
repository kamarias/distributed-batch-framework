package io.github.kamarias.dbf.system.infrastructure.db.store;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.dbf.system.entity.RolePermissionEntity;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceStore extends ServiceImpl<RolePermissionMapper, RolePermissionEntity> {
}
