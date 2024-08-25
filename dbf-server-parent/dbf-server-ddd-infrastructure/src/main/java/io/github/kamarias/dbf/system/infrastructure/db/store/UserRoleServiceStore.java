package io.github.kamarias.dbf.system.infrastructure.db.store;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.dbf.system.entity.UserRoleEntity;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceStore extends ServiceImpl<UserRoleMapper, UserRoleEntity> {


}
