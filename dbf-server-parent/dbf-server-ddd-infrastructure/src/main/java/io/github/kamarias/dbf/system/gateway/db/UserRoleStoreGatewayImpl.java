package io.github.kamarias.dbf.system.gateway.db;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.kamarias.dbf.system.entity.UserRoleEntity;
import io.github.kamarias.dbf.system.gateway.UserRoleStoreGateway;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.UserRoleMapper;
import io.github.kamarias.dbf.system.infrastructure.db.store.UserRoleServiceStore;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserRoleStoreGatewayImpl implements UserRoleStoreGateway {


    private final UserRoleServiceStore userRoleServiceStore;


    private final UserRoleMapper userRoleMapper;

    public UserRoleStoreGatewayImpl(UserRoleServiceStore userRoleServiceStore, UserRoleMapper userRoleMapper) {
        this.userRoleServiceStore = userRoleServiceStore;
        this.userRoleMapper = userRoleMapper;
    }


    @Override
    public boolean maintainUserRole(String userId, Set<String> roles) {
        userRoleServiceStore.remove(Wrappers.lambdaQuery(UserRoleEntity.class)
                .eq(UserRoleEntity::getUserId, userId));
        userRoleMapper.batchInsert(roles.stream().parallel().map(x -> {
            UserRoleEntity item = new UserRoleEntity();
            item.setUserId(userId);
            item.setRoleId(x);
            return item;
        }).collect(Collectors.toList()));
        return true;
    }


}
