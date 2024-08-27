package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.context.LoginUserContext;
import io.github.kamarias.dbf.system.model.LoginModel;
import io.github.kamarias.dbf.system.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LoginServiceTranslate {


    LoginUserContext toLoginUserModelByUserModel(UserModel dto);

    UserModel toUserModelByLoginUserContext(LoginUserContext userContext);
}
