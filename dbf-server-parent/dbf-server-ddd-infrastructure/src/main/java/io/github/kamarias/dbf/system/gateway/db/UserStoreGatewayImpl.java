package io.github.kamarias.dbf.system.gateway.db;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.kamarias.dbf.system.dto.UserDto;
import io.github.kamarias.dbf.system.entity.UserEntity;
import io.github.kamarias.dbf.system.gateway.UserStoreGateway;
import io.github.kamarias.dbf.system.infrastructure.db.mapper.UserMapper;
import io.github.kamarias.dbf.system.infrastructure.db.store.UserServiceStore;
import io.github.kamarias.dbf.system.translate.UserStoreTranslate;
import org.springframework.stereotype.Service;

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
    public boolean creatUser(UserDto userDto) {
        UserEntity ue = translate.toUserEntityByUserDto(userDto);
        return userServiceStore.save(ue);
    }


}
