package io.github.kamarias.dbf.system.infrastructure.db.store;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.dbf.system.entity.RouterRoleEntity;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.RouterRoleMapper;
import org.springframework.stereotype.Service;

@Service
public class RouterRoleServiceStore extends ServiceImpl<RouterRoleMapper, RouterRoleEntity> {
}
