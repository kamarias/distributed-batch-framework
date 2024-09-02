package io.github.kamarias.dbf.system.gateway.db;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.kamarias.dbf.system.dto.RoleDto;
import io.github.kamarias.dbf.system.entity.RoleEntity;
import io.github.kamarias.dbf.system.entity.UserRoleEntity;
import io.github.kamarias.dbf.system.gateway.UserRoleStoreGateway;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.UserRoleMapper;
import io.github.kamarias.dbf.system.infrastructure.db.store.UserRoleServiceStore;
import io.github.kamarias.dbf.system.translate.RoleStoreTranslate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserRoleStoreGatewayImpl implements UserRoleStoreGateway {


    private final UserRoleServiceStore userRoleServiceStore;


    private final UserRoleMapper userRoleMapper;

    private final RoleStoreTranslate translate;

    public UserRoleStoreGatewayImpl(UserRoleServiceStore userRoleServiceStore, UserRoleMapper userRoleMapper, RoleStoreTranslate translate) {
        this.userRoleServiceStore = userRoleServiceStore;
        this.userRoleMapper = userRoleMapper;
        this.translate = translate;
    }


    @Override
    public boolean maintainUserRole(String userId, Set<String> roles) {
        removeUserRoleByUserId(userId);
        if (Objects.isNull(roles) || roles.isEmpty()) {
            return true;
        }
        userRoleMapper.batchInsert(roles.stream().map(x -> {
            UserRoleEntity item = new UserRoleEntity();
            item.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            item.setUserId(userId);
            item.setRoleId(x);
            return item;
        }).collect(Collectors.toList()));
        return true;
    }


    @Override
    public List<RoleDto> findRoleListByAccount(String userId) {
        List<RoleEntity> roleEntityList = userRoleMapper.findRoleListByUserId(userId);
        List<RoleDto> roleDtoList = translate.toRoleDtoListByRoleEntityList(roleEntityList);
        return roleDtoList;
    }

    @Override
    public boolean removeUserRoleByUserId(String userId) {
        return userRoleServiceStore.remove(Wrappers.lambdaQuery(UserRoleEntity.class)
                .eq(UserRoleEntity::getUserId, userId));
    }


    @Override
    public Set<String> getRoleIdsByUserId(String userId) {
        Set<String> userRoles = userRoleMapper.getRoleIdsByUserId(userId);
        return Objects.isNull(userRoles) ? Collections.emptySet() : userRoles;
    }
}
