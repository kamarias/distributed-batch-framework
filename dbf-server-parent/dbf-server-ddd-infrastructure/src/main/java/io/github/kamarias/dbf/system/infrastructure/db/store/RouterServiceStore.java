package io.github.kamarias.dbf.system.infrastructure.db.store;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.dbf.system.entity.RouterEntity;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.RouterMapper;
import org.springframework.stereotype.Service;

@Service
public class RouterServiceStore extends ServiceImpl<RouterMapper, RouterEntity> {
}
