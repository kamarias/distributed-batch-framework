package io.github.kamarias.dbf.system.service;

import io.github.kamarias.dbf.system.context.*;
import io.github.kamarias.dbf.system.domain.UserDomainService;
import io.github.kamarias.dbf.system.model.QueryUserModel;
import io.github.kamarias.dbf.system.model.RoleModel;
import io.github.kamarias.dbf.system.model.UserModel;
import io.github.kamarias.dbf.system.model.UserTableModel;
import io.github.kamarias.dbf.system.translate.UserServiceTranslate;
import io.github.kamarias.dto.DDDContext;
import io.github.kamarias.vo.PageVO;
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

    public DDDContext<Void, Void> creatUser(DDDContext<UserContext, Void> context) {
        // 检验添加用户
        UserContext data = context.getRequest().getData();
        UserModel model = translate.toUserModelByUserContext(data);
        DDDContext<Void, Void> accountExists = userDomain.insertUserVerify(model);
        if (accountExists.isError()) {
            return accountExists;
        }
        DDDContext<Void, Void> addUser = userDomain.createUser(model);
        if (addUser.isError()) {
            return addUser;
        }
        return DDDContext.success();
    }

    public DDDContext<Void, Void> updateUser(DDDContext<UserContext, Void> request) {
        // 检验添加用户
        UserContext data = request.getRequest().getData();
        UserModel model = translate.toUserModelByUserContext(data);
        DDDContext<Void, Void> accountExists = userDomain.insertUserVerify(model);
        if (accountExists.isError()) {
            return accountExists;
        }
        DDDContext<Void, Void> addUser = userDomain.updateUser(model);
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


    public DDDContext<Void, Boolean> resetPassword(DDDContext<ResetPassContext, Void> request) {
        ResetPassContext context = request.getRequest().getData();
        return userDomain.resetPassword(context.getUserId(), context.getPassWord());
    }

    public DDDContext<QueryUserContext, PageVO<UserTableContext>> queryUserManageList(DDDContext<QueryUserContext, Void> request) {
        QueryUserContext data = request.getRequest().getData();
        QueryUserModel qum = translate.toQueryUserModelByQueryUserContext(data);
        DDDContext<QueryUserModel, PageVO<UserTableModel>> model = userDomain.queryUserTableList(qum);
        if (model.isError()) {
            return DDDContext.error(model.getMsg());
        }
        return DDDContext.success(translate.toUserTableContextPageVOByUserTableModelPageVO(model.getResponse().getData()));
    }


    public DDDContext<Void, Boolean> updateStatus(DDDContext<UserContext, Void> request) {
        UserContext data = request.getRequest().getData();
        DDDContext<Void, Boolean> updateStatus = userDomain.updateUserStatus(data.getId());
        if (updateStatus.isError()) {
            return DDDContext.error(updateStatus.getMsg());
        }
        return updateStatus;
    }


    public DDDContext<Void, Boolean> deleteUser(DDDContext<UserContext, Void> request) {
        UserContext data = request.getRequest().getData();
        DDDContext<Void, Boolean> updateStatus = userDomain.deleteUser(data.getId());
        if (updateStatus.isError()) {
            return DDDContext.error(updateStatus.getMsg());
        }
        return updateStatus;
    }

    public DDDContext<Void, UserContext> getUserInfoById(DDDContext<UserContext, Void> context) {
        UserContext data = context.getRequest().getData();
        DDDContext<Void, UserModel> um = userDomain.getUserInfo(data.getId());
        if (um.isError()) {
            return DDDContext.error(um.getMsg());
        }
        UserContext uc  = translate.toUserContextByUserModel(um.getResponse().getData());
        return DDDContext.success(uc);
    }
}
