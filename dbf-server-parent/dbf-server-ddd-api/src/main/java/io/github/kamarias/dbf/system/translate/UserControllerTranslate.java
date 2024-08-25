package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.context.UserContext;
import io.github.kamarias.dbf.system.vo.AddUserVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserControllerTranslate {


    UserContext toUserContextByAddUserVo(AddUserVo vo);


}
