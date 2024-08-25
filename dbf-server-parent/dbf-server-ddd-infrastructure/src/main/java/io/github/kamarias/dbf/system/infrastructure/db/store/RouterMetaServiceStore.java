package io.github.kamarias.dbf.system.infrastructure.db.store;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.dbf.system.entity.RouterMetaEntity;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.RouterMetaMapper;
import org.springframework.stereotype.Service;

@Service
public class RouterMetaServiceStore extends ServiceImpl<RouterMetaMapper, RouterMetaEntity> {

}
