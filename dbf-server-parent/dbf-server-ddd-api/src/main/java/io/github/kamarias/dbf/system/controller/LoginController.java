package io.github.kamarias.dbf.system.controller;

import io.github.kamarias.dbf.system.context.LoginUserContext;
import io.github.kamarias.dbf.system.model.LoginModel;
import io.github.kamarias.dbf.system.service.LoginService;
import io.github.kamarias.dbf.system.translate.LoginControllerTranslate;
import io.github.kamarias.dbf.system.vo.IdVo;
import io.github.kamarias.dbf.system.vo.LoginUser;
import io.github.kamarias.dbf.system.vo.LoginVo;
import io.github.kamarias.dto.AjaxResult;
import io.github.kamarias.dto.DDDContext;
import io.github.kamarias.token.AuthTokenService;
import io.github.kamarias.utils.SecurityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    private final AuthTokenService tokenService;

    private final LoginService loginService;

    private final LoginControllerTranslate translate;

    public LoginController(AuthTokenService tokenService, LoginService loginService, LoginControllerTranslate translate) {
        this.tokenService = tokenService;
        this.loginService = loginService;
        this.translate = translate;
    }

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public AjaxResult<Object> login(@RequestBody @Validated LoginVo form) {
        LoginModel loginVo = translate.toLoginModelByByLoginVo(form);
        DDDContext<Void, LoginUserContext> context = loginService.login(DDDContext.request(loginVo));
        if (context.isError()) {
            return AjaxResult.error(context.getMsg());
        }
        LoginUser loginUser = translate.toLoginUserByLoginModel(context.getResponse().getData());
        String token = tokenService.createToken(loginUser);
        System.out.println("++++++++++++++++++");
        System.out.println(loginUser);
        return AjaxResult.success("success", token);
    }

    /**
     * 切换角色登录 切换角色
     */
    @PostMapping("/switchRole")
    public AjaxResult<Object> switchRole(@RequestBody @Validated IdVo form) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        LoginUserContext loginUserContext = translate.toLoginUserContextByLoginUser(loginUser);
        // 设置需要登录的角色
        loginUserContext.setRoleId(form.getId());
        DDDContext<Void, LoginUserContext> context = loginService.switchRole(DDDContext.request(loginUserContext));
        if (context.isError()) {
            return AjaxResult.error(context.getMsg());
        }
        // 需要登录的角色
        LoginUser roleLoginUser = translate.toLoginUserByLoginModel(context.getResponse().getData());
        System.out.println("++++++++++++++++++");
        System.out.println(roleLoginUser);
        // 清理之前登录的token
        tokenService.deleteToken();
        String token = tokenService.createToken(roleLoginUser);
        return AjaxResult.success("success", token);
    }


    /**
     * 用户登出功能
     *
     * @return 返回一个AjaxResult对象，其中包含一个表示令牌删除成功与否的Boolean值
     */
    @GetMapping("/loginOut")
    public AjaxResult<Boolean> logout() {
        return AjaxResult.success(tokenService.deleteToken());
    }

}
