package io.github.kamarias.dbf.system.service;

import io.github.kamarias.dbf.system.context.LoginUserContext;
import io.github.kamarias.dbf.system.domain.UserDomainService;
import io.github.kamarias.dbf.system.model.LoginModel;
import io.github.kamarias.dbf.system.model.UserModel;
import io.github.kamarias.dbf.system.translate.LoginServiceTranslate;
import io.github.kamarias.dto.DDDContext;
import org.springframework.stereotype.Service;

import java.util.HashSet;

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
    public DDDContext<LoginModel, LoginUserContext> login(DDDContext<LoginModel, Void> context) {
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
        LoginUserContext loginUserContext = translate.toLoginUserModelByUserModel(user);
        // 注入权限信息
        loginUserContext.setRoles(new HashSet<>());
        loginUserContext.setPermissions(new HashSet<>());
        return DDDContext.success(login, loginUserContext);
    }

}
