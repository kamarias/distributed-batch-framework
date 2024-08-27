package io.github.kamarias.dbf.system.translate;


import io.github.kamarias.dbf.system.dto.RoleDto;
import io.github.kamarias.dbf.system.dto.UserDto;
import io.github.kamarias.dbf.system.model.RoleModel;
import io.github.kamarias.dbf.system.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDomainTranslate {

    UserModel toUserModelByUserDto(UserDto userDto);

    UserDto toUserDtoByUserModel(UserModel model);

    List<RoleModel> toRoleModelListByRoleDtoList(List<RoleDto> roles);


}
