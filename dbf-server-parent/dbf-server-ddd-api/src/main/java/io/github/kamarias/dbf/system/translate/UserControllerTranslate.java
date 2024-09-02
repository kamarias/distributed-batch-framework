package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.context.QueryUserContext;
import io.github.kamarias.dbf.system.context.ResetPassContext;
import io.github.kamarias.dbf.system.context.RoleContext;
import io.github.kamarias.dbf.system.context.UserContext;
import io.github.kamarias.dbf.system.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserControllerTranslate {


    UserContext toUserContextByAddUserVo(AddUserVo vo);


    List<RoleVO> toRoleVOListByRoleContextList(List<RoleContext> data);


    @Mappings({
            @Mapping(target = "userId", source = "id")
    })
    ResetPassContext toResetPassContextByResetPassWordVo(ResetPassWordVo form);


    QueryUserContext toQueryUserVoByQueryUserContext(QueryUserVo form);

    UserContext toUserContextByIdVo(IdVo form);

    UserContext toUserContextByUpdateUserVo(UpdateUserVo form);

    UpdateUserVo toUpdateUSerVoByUserContext(UserContext data);
}
