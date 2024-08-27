package io.github.kamarias.dbf.system.gateway.db;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.kamarias.dbf.system.dto.RoleDto;
import io.github.kamarias.dbf.system.entity.RoleEntity;
import io.github.kamarias.dbf.system.entity.UserRoleEntity;
import io.github.kamarias.dbf.system.gateway.RoleStoreGateway;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.RoleMapper;
import io.github.kamarias.dbf.system.infrastructure.db.store.RoleServiceStore;
import io.github.kamarias.dbf.system.infrastructure.db.store.UserRoleServiceStore;
import io.github.kamarias.dbf.system.translate.RoleStoreTranslate;
import org.springframework.stereotype.Service;

@Service
public class RoleStoreGatewayImpl implements RoleStoreGateway {

    private final RoleServiceStore roleServiceStore;

    private final RoleMapper roleMapper;

    private final UserRoleServiceStore userRoleServiceStore;

    private final RoleStoreTranslate roleStoreTranslate;


    public RoleStoreGatewayImpl(RoleServiceStore roleServiceStore, RoleMapper roleMapper, UserRoleServiceStore userRoleServiceStore, RoleStoreTranslate roleStoreTranslate) {
        this.roleServiceStore = roleServiceStore;
        this.roleMapper = roleMapper;
        this.userRoleServiceStore = userRoleServiceStore;
        this.roleStoreTranslate = roleStoreTranslate;
    }

    @Override
    public RoleDto randomFindRoleByUserId(String userId) {
        RoleEntity roleEntity = roleMapper.randomGetRoleByUserId(userId);
        return roleStoreTranslate.toRoleDtoByRoleEntity(roleEntity);
    }


    @Override
    public RoleDto findRoleByUserIdAndRoleId(String userId, String roleId) {
        RoleEntity roleEntity = roleMapper.randomGetRoleByUserIdAndRoleId(userId, roleId);
        return roleStoreTranslate.toRoleDtoByRoleEntity(roleEntity);
    }
}
