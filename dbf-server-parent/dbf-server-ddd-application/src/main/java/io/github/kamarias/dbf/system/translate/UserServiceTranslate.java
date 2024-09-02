package io.github.kamarias.dbf.system.translate;


import io.github.kamarias.dbf.system.context.QueryUserContext;
import io.github.kamarias.dbf.system.context.RoleContext;
import io.github.kamarias.dbf.system.context.UserContext;
import io.github.kamarias.dbf.system.context.UserTableContext;
import io.github.kamarias.dbf.system.model.QueryUserModel;
import io.github.kamarias.dbf.system.model.RoleModel;
import io.github.kamarias.dbf.system.model.UserModel;
import io.github.kamarias.dbf.system.model.UserTableModel;
import io.github.kamarias.vo.PageVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserServiceTranslate {


    UserModel toUserModelByUserContext(UserContext context);


    List<RoleContext> toRoleContextListByRoleModelList(List<RoleModel> data);


    QueryUserModel toQueryUserModelByQueryUserContext(QueryUserContext data);

    PageVO<UserTableContext> toUserTableContextPageVOByUserTableModelPageVO(PageVO<UserTableModel> data);

    UserContext toUserContextByUserModel(UserModel data);
}
