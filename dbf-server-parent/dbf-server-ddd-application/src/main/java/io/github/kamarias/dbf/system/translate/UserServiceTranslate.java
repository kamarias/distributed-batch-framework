package io.github.kamarias.dbf.system.translate;


import io.github.kamarias.dbf.system.context.UserContext;
import io.github.kamarias.dbf.system.model.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserServiceTranslate {


    UserModel toUserModelByUserContext(UserContext context);


}
