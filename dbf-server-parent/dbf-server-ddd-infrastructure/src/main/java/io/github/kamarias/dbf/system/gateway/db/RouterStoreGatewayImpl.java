package io.github.kamarias.dbf.system.gateway.db;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.kamarias.dbf.system.dto.RouterDto;
import io.github.kamarias.dbf.system.entity.RouterEntity;
import io.github.kamarias.dbf.system.gateway.RouterStoreGateway;
import io.github.kamarias.dbf.system.infrastructure.db.store.RouterServiceStore;
import io.github.kamarias.dbf.system.translate.RouterStoreTranslate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouterStoreGatewayImpl implements RouterStoreGateway {

    private final RouterServiceStore routerServiceStore;


    private final RouterStoreTranslate routerStoreTranslate;

    public RouterStoreGatewayImpl(RouterServiceStore routerServiceStore, RouterStoreTranslate routerStoreTranslate) {
        this.routerServiceStore = routerServiceStore;
        this.routerStoreTranslate = routerStoreTranslate;
    }

    @Override
    public List<RouterDto> findAllRouterByStatus(Integer status) {
        Wrapper<RouterEntity> wrapper = Wrappers.lambdaQuery(RouterEntity.class).
                eq(RouterEntity::getState, status);
        List<RouterEntity> routers = routerServiceStore.list(wrapper);
        return routerStoreTranslate.toRouterDtoListByRouterEntityList(routers);
    }


}
