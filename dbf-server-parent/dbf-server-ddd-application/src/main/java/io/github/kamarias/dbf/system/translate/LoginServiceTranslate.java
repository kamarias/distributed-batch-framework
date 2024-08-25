package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.context.LoginContext;
import io.github.kamarias.dbf.system.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LoginServiceTranslate {

    @Mappings({
            @Mapping(source = "loginContext.username", target = "account")
    })
    UserModel toUserModelByLoginContext(LoginContext loginContext);

    LoginContext toLoginContextByUserModel(UserModel userModel);


}
