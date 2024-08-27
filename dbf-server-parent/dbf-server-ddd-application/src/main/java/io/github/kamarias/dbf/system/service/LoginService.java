package io.github.kamarias.dbf.system.service;

import io.github.kamarias.dbf.system.context.LoginUserContext;
import io.github.kamarias.dbf.system.domain.UserDomainService;
import io.github.kamarias.dbf.system.model.LoginModel;
import io.github.kamarias.dbf.system.model.RoleModel;
import io.github.kamarias.dbf.system.model.UserModel;
import io.github.kamarias.dbf.system.translate.LoginServiceTranslate;
import io.github.kamarias.dto.DDDContext;
import io.github.kamarias.utils.string.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class LoginService {


    private final UserDomainService userDomain;

    private final LoginServiceTranslate translate;


    public LoginService(UserDomainService userDomain, LoginServiceTranslate translate) {
        this.userDomain = userDomain;
        this.translate = translate;
    }


    /**
     * 用户登录方法
     * 该方法主要用于验证用户凭据并生成登录上下文
     *
     * @param context 登录上下文，包含用户账户和密码信息
     * @return 返回一个上下文响应对象，其中包含登录结果和可能的错误信息
     */
    public DDDContext<Void, LoginUserContext> login(DDDContext<LoginModel, Void> context) {
        // 上下文获取用户模型
        LoginModel login = context.getRequest().getData();
        DDDContext<String, UserModel> ucr = userDomain.findUser(login.getUsername());
        if (ucr.isError()) {
            return DDDContext.error("用户不存在");
        }
        UserModel user = ucr.getResponse().getData();
        // 比较密码
        DDDContext<Void, Void> matchesPassword = userDomain.matchesPassword(login.getPassword(), user.getPassWord());
        if (matchesPassword.isError()) {
            return DDDContext.error("密码错误，请重试");
        }
        return injectRolePermissions(user, null);
    }

    public DDDContext<Void,LoginUserContext> switchRole(DDDContext<LoginUserContext, Void> request) {
        LoginUserContext userContext = request.getRequest().getData();
        UserModel model = translate.toUserModelByLoginUserContext(userContext);
        return injectRolePermissions(model, userContext.getRoleId());
    }


    /**
     * 注入权限信息
     * @param userModel 用户模型
     * @param roleId 角色id
     * @return 返回注入后的上下文
     */
    private DDDContext<Void, LoginUserContext> injectRolePermissions(UserModel userModel, String roleId) {
        LoginUserContext loginUserContext = translate.toLoginUserModelByUserModel(userModel);
        // 存在角色id时，用角色Id 登录，不存在时随机选取一位角色
        DDDContext<Void, RoleModel> roleContext = StringUtils.isEmpty(roleId) ?
                userDomain.randomGetRoleByUserId(userModel.getId()) :
                userDomain.getRoleByUserIdAndRoleId(userModel.getId(), roleId);
        if (roleContext.isError()) {
            return DDDContext.error(roleContext.getMsg());
        }
        RoleModel roleModel = roleContext.getResponse().getData();
        DDDContext<Void, Set<String>> permissionCodes = userDomain.getPermissionCodesByRoleId(roleModel.getId());
        if (permissionCodes.isError()) {
            return DDDContext.error("获取权限信息异常，请联系管理员");
        }
        // 注入权限信息
        String roleCode = roleModel.getCode();
        loginUserContext.setRoles(StringUtils.isEmpty(roleCode) ? new HashSet<>() : new HashSet<>(Collections.singletonList(roleCode)));
        loginUserContext.setPermissions(permissionCodes.getResponse().getData());
        return DDDContext.success(loginUserContext);
    }

}
