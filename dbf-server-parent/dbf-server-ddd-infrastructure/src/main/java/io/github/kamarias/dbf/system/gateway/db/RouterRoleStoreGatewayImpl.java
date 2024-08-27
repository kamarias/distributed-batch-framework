package io.github.kamarias.dbf.system.gateway.db;

import io.github.kamarias.dbf.system.gateway.RouterRoleStoreGateway;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.RouterRoleMapper;
import io.github.kamarias.dbf.system.infrastructure.db.store.RouterRoleServiceStore;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class RouterRoleStoreGatewayImpl implements RouterRoleStoreGateway {

    private final RouterRoleServiceStore routerRoleServiceStore;

    private final RouterRoleMapper routerRoleMapper;

    public RouterRoleStoreGatewayImpl(RouterRoleServiceStore routerRoleServiceStore, RouterRoleMapper routerRoleMapper) {
        this.routerRoleServiceStore = routerRoleServiceStore;
        this.routerRoleMapper = routerRoleMapper;
    }

    @Override
    public List<String> getRoleCodeByRouterId(String routerId) {
        List<String> roleCodes =  routerRoleMapper.getRoleCodeByRouterId(routerId);


        return Objects.isNull(roleCodes) ? Collections.emptyList() : roleCodes;
    }


}
