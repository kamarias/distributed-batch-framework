package io.github.kamarias.dbf.system.translate;


import io.github.kamarias.dbf.system.context.RoleContext;
import io.github.kamarias.dbf.system.context.UserContext;
import io.github.kamarias.dbf.system.model.RoleModel;
import io.github.kamarias.dbf.system.model.UserModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserServiceTranslate {


    UserModel toUserModelByUserContext(UserContext context);


    List<RoleContext> toRoleContextListByRoleModelList(List<RoleModel> data);


}
