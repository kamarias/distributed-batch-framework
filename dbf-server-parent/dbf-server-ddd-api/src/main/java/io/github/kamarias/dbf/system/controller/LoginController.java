package io.github.kamarias.dbf.system.controller;

import io.github.kamarias.dbf.system.context.LoginContext;
import io.github.kamarias.dbf.system.service.LoginService;
import io.github.kamarias.dbf.system.translate.LoginControllerTranslate;
import io.github.kamarias.dbf.system.vo.LoginUser;
import io.github.kamarias.dbf.system.vo.LoginVo;
import io.github.kamarias.dto.AjaxResult;
import io.github.kamarias.dto.ContextResponse;
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
        LoginContext loginContext = translate.toLoginContextByLoginVo(form);
        ContextResponse<LoginContext> response = loginService.login(loginContext);
        if (response.isError()) {
            return AjaxResult.error(response.getMsg());
        }
        LoginUser loginUser = translate.toLoginUserByLoginContext(loginContext);
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
