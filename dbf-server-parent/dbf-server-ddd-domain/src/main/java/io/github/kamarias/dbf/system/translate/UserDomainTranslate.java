package io.github.kamarias.dbf.system.translate;


import io.github.kamarias.dbf.system.dto.UserDto;
import io.github.kamarias.dbf.system.model.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDomainTranslate {

    UserModel toUserModelByUserDto(UserDto userDto);

    UserDto toUserDtoByUserModel(UserModel model);

}
