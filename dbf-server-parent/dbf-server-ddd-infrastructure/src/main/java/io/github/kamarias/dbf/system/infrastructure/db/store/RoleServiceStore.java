package io.github.kamarias.dbf.system.infrastructure.db.store;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.dbf.system.entity.RoleEntity;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.RoleMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceStore extends ServiceImpl<RoleMapper, RoleEntity> {
}
