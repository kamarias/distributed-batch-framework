package io.github.kamarias.dbf.system.gateway.db;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.kamarias.dbf.system.dto.UserDto;
import io.github.kamarias.dbf.system.entity.UserEntity;
import io.github.kamarias.dbf.system.gateway.UserStoreGateway;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.UserMapper;
import io.github.kamarias.dbf.system.infrastructure.db.store.UserServiceStore;
import io.github.kamarias.dbf.system.model.QueryUserModel;
import io.github.kamarias.dbf.system.translate.UserStoreTranslate;
import io.github.kamarias.utils.string.StringUtils;
import io.github.kamarias.vo.PageVO;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserStoreGatewayImpl implements UserStoreGateway {


    private final UserServiceStore userServiceStore;


    private final UserMapper userMapper;


    private final UserStoreTranslate translate;


    public UserStoreGatewayImpl(UserServiceStore userServiceStore, UserMapper userMapper, UserStoreTranslate translate) {
        this.userServiceStore = userServiceStore;
        this.userMapper = userMapper;
        this.translate = translate;
    }


    @Override
    public UserDto selectUserByPhone(String phone) {
        UserEntity one = userServiceStore.
                getOne(Wrappers.lambdaQuery(UserEntity.class)
                        .eq(UserEntity::getPhone, phone));
        return translate.toUserDtoByUserEntity(one);
    }

    @Override
    public UserDto selectUserByEmail(String email) {
        UserEntity one = userServiceStore.
                getOne(Wrappers.lambdaQuery(UserEntity.class)
                        .eq(UserEntity::getEmail, email));
        return translate.toUserDtoByUserEntity(one);
    }

    @Override
    public UserDto selectUserByAccount(String userName) {
        UserEntity one = userServiceStore.
                getOne(Wrappers.lambdaQuery(UserEntity.class)
                        .eq(UserEntity::getAccount, userName));
        return translate.toUserDtoByUserEntity(one);
    }

    @Override
    public boolean phoneExists(String phone) {
        return userServiceStore.exists(
                (Wrappers.lambdaQuery(UserEntity.class).eq(UserEntity::getPhone, phone))
        );
    }

    @Override
    public boolean accountExists(String account) {
        return userServiceStore.exists(
                (Wrappers.lambdaQuery(UserEntity.class)
                        .eq(UserEntity::getAccount, account))
        );
    }

    @Override
    public boolean emailExists(String email) {
        return userServiceStore.exists(
                (Wrappers.lambdaQuery(UserEntity.class)
                        .eq(UserEntity::getEmail, email))
        );
    }

    @Override
    public UserDto creatUser(UserDto userDto) {
        UserEntity ue = translate.toUserEntityByUserDto(userDto);
        boolean save = userServiceStore.save(ue);
        if (!save) {
            return null;
        }
        return translate.toUserDtoByUserEntity(ue);
    }

    @Override
    public UserDto selectUserByUserId(String userId) {
        UserEntity byId = userServiceStore.getById(userId);
        return translate.toUserDtoByUserEntity(byId);
    }

    @Override
    public Boolean updateUser(UserDto userDto) {
        UserEntity userEntity = translate.toUserEntityByUserDto(userDto);
        return userServiceStore.updateById(userEntity);
    }

    @Override
    public PageVO<UserDto> queryUserTableList(QueryUserModel qum) {
        Page<UserEntity> page = Page.of(qum.getPageNum(), qum.getPageSize());
        Page<UserEntity> userPage = userServiceStore.page(page, Wrappers.lambdaQuery(UserEntity.class)
                .eq(StringUtils.isNotBlank(qum.getAccount()), UserEntity::getAccount, qum.getAccount())
                .eq(StringUtils.isNotBlank(qum.getEmail()), UserEntity::getEmail, qum.getEmail())
                .eq(StringUtils.isNotBlank(qum.getPhone()), UserEntity::getPhone, qum.getPhone())
                .eq(StringUtils.isNotBlank(qum.getNickName()), UserEntity::getNickName, qum.getNickName())
                .eq(Objects.nonNull(qum.getStatus()), UserEntity::getStatus, qum.getStatus()));
        Page<UserDto> upd = translate.toPageUserDtoByUserEntityPage(userPage);
        return new PageVO<>(upd.getCurrent(), upd.getTotal(), upd.getRecords());
    }

    @Override
    public boolean deleteUserByUserId(String userId) {
        return userServiceStore.removeById(userId);
    }
}
