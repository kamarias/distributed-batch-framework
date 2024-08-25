package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.context.LoginContext;
import io.github.kamarias.dbf.system.vo.LoginUser;
import io.github.kamarias.dbf.system.vo.LoginVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginControllerTranslate {


    LoginContext toLoginContextByLoginVo(LoginVo loginVo);

    LoginUser toLoginUserByLoginContext(LoginContext context);

}
