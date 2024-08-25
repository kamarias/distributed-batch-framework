package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.dto.UserDto;
import io.github.kamarias.dbf.system.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserStoreTranslate {


    UserDto toUserDtoByUserEntity(UserEntity userEntity);


    UserEntity toUserEntityByUserDto(UserDto userDto);
}
