package io.github.kamarias.dbf.system.service;

import io.github.kamarias.dbf.system.context.LoginUserContext;
import io.github.kamarias.dbf.system.context.RoleContext;
import io.github.kamarias.dbf.system.context.UserContext;
import io.github.kamarias.dbf.system.domain.UserDomainService;
import io.github.kamarias.dbf.system.model.RoleModel;
import io.github.kamarias.dbf.system.model.UserModel;
import io.github.kamarias.dbf.system.translate.UserServiceTranslate;
import io.github.kamarias.dto.DDDContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDomainService userDomain;

    private final UserServiceTranslate translate;

    public UserService(UserDomainService userDomain, UserServiceTranslate translate) {
        this.userDomain = userDomain;
        this.translate = translate;
    }

    public DDDContext<Void, Void> creatUser(UserContext context) {
        // 检验添加用户
        UserModel model = translate.toUserModelByUserContext(context);
        DDDContext<Void, Void> accountExists = userDomain.createUserVerify(model);
        if (accountExists.isError()) {
            return accountExists;
        }
        DDDContext<Void, Void> addUser = userDomain.createUser(model);
        if (addUser.isError()) {
            return addUser;
        }
        return DDDContext.success();
    }

    public DDDContext<Void, List<RoleContext>> getLoginUserRole(DDDContext<LoginUserContext, Void> request) {
        LoginUserContext loginUser = request.getRequest().getData();
        DDDContext<Void, List<RoleModel>> roleModel = userDomain.findUserRole(loginUser.getId());
        if (roleModel.isError()) {
            return DDDContext.error(roleModel.getMsg());
        }
        List<RoleContext> userRoles = translate.toRoleContextListByRoleModelList(roleModel.getResponse().getData());
        return DDDContext.success(userRoles);
    }


}
