package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.context.RoleContext;
import io.github.kamarias.dbf.system.context.UserContext;
import io.github.kamarias.dbf.system.vo.AddUserVo;
import io.github.kamarias.dbf.system.vo.RoleVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserControllerTranslate {


    UserContext toUserContextByAddUserVo(AddUserVo vo);


    List<RoleVO> toRoleVOListByRoleContextList(List<RoleContext> data);


}
