package io.github.kamarias.dbf.system.gateway.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.kamarias.dbf.enums.BoolFlagEnum;
import io.github.kamarias.dbf.system.dto.RoleDto;
import io.github.kamarias.dbf.system.entity.RoleEntity;
import io.github.kamarias.dbf.system.gateway.RoleStoreGateway;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.RoleMapper;
import io.github.kamarias.dbf.system.infrastructure.db.store.RoleServiceStore;
import io.github.kamarias.dbf.system.infrastructure.db.store.UserRoleServiceStore;
import io.github.kamarias.dbf.system.model.QueryRoleModel;
import io.github.kamarias.dbf.system.translate.RoleStoreTranslate;
import io.github.kamarias.utils.string.StringUtils;
import io.github.kamarias.vo.PageVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    @Override
    public List<RoleDto> getAllRole() {
        List<RoleEntity> list = roleServiceStore.list();
        return roleStoreTranslate.toRoleDtoListByRoleEntityList(list);
    }

    @Override
    public PageVO<RoleDto> queryRoleTableList(QueryRoleModel qum) {
        Page<RoleEntity> page;
        LambdaQueryWrapper<RoleEntity> query = Wrappers.lambdaQuery(RoleEntity.class);
        query.like(StringUtils.isNotBlank(qum.getName()), RoleEntity::getName, qum.getName())
                .eq(StringUtils.isNotBlank(qum.getCode()), RoleEntity::getCode, qum.getCode())
                .eq(Objects.nonNull(qum.getStatus()), RoleEntity::getStatus, qum.getStatus());
        if (StringUtils.isNotBlank(qum.getPermissionCode())) {
            QueryWrapper<RoleEntity> wrapper = new QueryWrapper<>();
            wrapper.like(StringUtils.isNotBlank(qum.getName()), "r.name", qum.getName())
                    .eq(StringUtils.isNotBlank(qum.getCode()), "r.code", qum.getCode())
                    .eq(Objects.nonNull(qum.getStatus()), "r.status", qum.getStatus())
                    .eq(StringUtils.isNotBlank(qum.getPermissionCode()), "p.code", qum.getPermissionCode())
                    .eq("r.del_flag", BoolFlagEnum.NOT.getCode())
                    .eq("p.del_flag", BoolFlagEnum.NOT.getCode());
            page = roleMapper.getList(new Page<>(qum.getPageNum(), qum.getPageSize()), wrapper);
        } else {
            page = roleServiceStore.page(new Page<>(qum.getPageNum(), qum.getPageSize()), query);
        }
        Page<RoleDto> upd = roleStoreTranslate.toPageUserDtoByRoleEntityPage(page);
        return new PageVO<>(upd.getCurrent(), upd.getTotal(), upd.getRecords());

    }
}
