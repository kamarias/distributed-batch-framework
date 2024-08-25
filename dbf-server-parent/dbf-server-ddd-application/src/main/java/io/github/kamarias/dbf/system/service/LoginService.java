package io.github.kamarias.dbf.system.service;

import io.github.kamarias.dbf.system.context.LoginContext;
import io.github.kamarias.dbf.system.domain.UserDomainService;
import io.github.kamarias.dbf.system.model.UserModel;
import io.github.kamarias.dbf.system.translate.LoginServiceTranslate;
import io.github.kamarias.dto.ContextResponse;
import org.springframework.stereotype.Service;

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
    public ContextResponse<LoginContext> login(LoginContext context) {
        // 上下文获取用户模型
        UserModel userModel = translate.toUserModelByLoginContext(context);
        ContextResponse<UserModel> ucr = userDomain.findUser(userModel.getAccount());
        if (ucr.isError()){
            return ContextResponse.error("用户不存在");
        }
        UserModel user = ucr.getData();
        ContextResponse<Void> matchesPassword = userDomain.matchesPassword(context.getPassword(), user.getPassWord());
        if (matchesPassword.isError()){
            return ContextResponse.error("密码错误，请重试");
        }
        LoginContext result = translate.toLoginContextByUserModel(user);
        return ContextResponse.success(result);
    }

}
