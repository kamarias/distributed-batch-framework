package io.github.kamarias.dbf.system.infrastructure.db.store;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.dbf.system.entity.UserEntity;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceStore extends ServiceImpl<UserMapper, UserEntity> {


}
