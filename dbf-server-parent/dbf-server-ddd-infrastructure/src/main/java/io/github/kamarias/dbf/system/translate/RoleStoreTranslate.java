package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.dto.RoleDto;
import io.github.kamarias.dbf.system.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleStoreTranslate {


    List<RoleDto> toRoleDtoListByRoleEntityList(List<RoleEntity> roleEntityList);


}
