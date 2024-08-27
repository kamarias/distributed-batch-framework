package io.github.kamarias.dbf.system.gateway.db;

import io.github.kamarias.dbf.system.gateway.PermissionStoreGateway;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.PermissionMapper;
import io.github.kamarias.dbf.system.infrastructure.db.store.PermissionServiceStore;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class PermissionStoreGatewayImpl implements PermissionStoreGateway {

    private final PermissionServiceStore permissionServiceStore;

    private final PermissionMapper permissionMapper;

    public PermissionStoreGatewayImpl(PermissionServiceStore permissionServiceStore, PermissionMapper permissionMapper) {
        this.permissionServiceStore = permissionServiceStore;
        this.permissionMapper = permissionMapper;
    }


    @Override
    public Set<String> getPermissionCodeByRoleId(String roleId) {
        return permissionMapper.getPermissionCodeByRoleId(roleId);
    }

}
