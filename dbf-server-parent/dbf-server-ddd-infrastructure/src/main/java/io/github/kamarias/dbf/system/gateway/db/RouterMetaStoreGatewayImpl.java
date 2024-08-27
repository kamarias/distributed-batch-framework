package io.github.kamarias.dbf.system.gateway.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.kamarias.dbf.system.dto.RouterMetaDto;
import io.github.kamarias.dbf.system.entity.RouterMetaEntity;
import io.github.kamarias.dbf.system.gateway.RouterMetaStoreGateway;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.RouterMetaMapper;
import io.github.kamarias.dbf.system.infrastructure.db.store.RouterMetaServiceStore;
import io.github.kamarias.dbf.system.translate.RouterMetaStoreTranslate;
import org.springframework.stereotype.Service;

@Service
public class RouterMetaStoreGatewayImpl implements RouterMetaStoreGateway {


    private final RouterMetaServiceStore routerMetaService;

    private final RouterMetaMapper routerMetaMapper;

    private final RouterMetaStoreTranslate routerMetaStoreTranslate;

    public RouterMetaStoreGatewayImpl(RouterMetaServiceStore routerMetaService, RouterMetaMapper routerMetaMapper, RouterMetaStoreTranslate routerMetaStoreTranslate) {
        this.routerMetaService = routerMetaService;
        this.routerMetaMapper = routerMetaMapper;
        this.routerMetaStoreTranslate = routerMetaStoreTranslate;
    }


    @Override
    public RouterMetaDto getRouterMetaByRouterId(String routerId) {
        RouterMetaEntity routerMeta =  routerMetaMapper.findByRouterIdRouter(routerId);
        return routerMetaStoreTranslate.toRouterMetaDtoByRouterMetaEntity(routerMeta);
    }



}
