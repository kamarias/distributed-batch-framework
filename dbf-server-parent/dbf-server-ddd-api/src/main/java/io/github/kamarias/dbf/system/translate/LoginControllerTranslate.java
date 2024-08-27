package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.context.LoginUserContext;
import io.github.kamarias.dbf.system.model.LoginModel;
import io.github.kamarias.dbf.system.vo.LoginUser;
import io.github.kamarias.dbf.system.vo.LoginVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginControllerTranslate {


    LoginModel toLoginModelByByLoginVo(LoginVo loginVo);


    LoginUser toLoginUserByLoginModel(LoginUserContext model);


    LoginUserContext toLoginUserContextByLoginUser(LoginUser loginUser);


}
