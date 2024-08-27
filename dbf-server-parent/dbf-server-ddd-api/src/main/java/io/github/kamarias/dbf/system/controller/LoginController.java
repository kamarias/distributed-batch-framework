package io.github.kamarias.dbf.system.controller;

import io.github.kamarias.dbf.system.context.LoginUserContext;
import io.github.kamarias.dbf.system.model.LoginModel;
import io.github.kamarias.dbf.system.service.LoginService;
import io.github.kamarias.dbf.system.translate.LoginControllerTranslate;
import io.github.kamarias.dbf.system.vo.LoginUser;
import io.github.kamarias.dbf.system.vo.LoginVo;
import io.github.kamarias.dto.AjaxResult;
import io.github.kamarias.dto.DDDContext;
import io.github.kamarias.token.AuthTokenService;
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
        DDDContext<LoginModel, LoginUserContext> context = loginService.login(DDDContext.request(loginVo));
        if (context.isError()) {
            return AjaxResult.error(context.getMsg());
        }
        LoginUser loginUser = translate.toLoginUserByLoginModel(context.getResponse().getData());
        String token = tokenService.createToken(loginUser);
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
