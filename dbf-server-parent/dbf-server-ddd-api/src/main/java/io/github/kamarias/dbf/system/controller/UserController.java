package io.github.kamarias.dbf.system.controller;


import io.github.kamarias.annotation.RequiresPermissions;
import io.github.kamarias.dbf.system.context.*;
import io.github.kamarias.dbf.system.service.UserService;
import io.github.kamarias.dbf.system.translate.LoginControllerTranslate;
import io.github.kamarias.dbf.system.translate.UserControllerTranslate;
import io.github.kamarias.dbf.system.vo.*;
import io.github.kamarias.dto.AjaxResult;
import io.github.kamarias.dto.DDDContext;
import io.github.kamarias.utils.SecurityUtils;
import io.github.kamarias.vo.PageVO;
import io.github.kamarias.web.annotation.RepeatSubmit;
import io.github.kamarias.web.annotation.WebLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 10:13
 */
@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    private final UserControllerTranslate translate;

    private final LoginControllerTranslate loginControllerTranslate;


    public UserController(UserService userService, UserControllerTranslate translate, LoginControllerTranslate loginControllerTranslate) {
        this.userService = userService;
        this.translate = translate;
        this.loginControllerTranslate = loginControllerTranslate;
    }


    /**
     * 查询用户信息列表
     */
    @WebLog("查询用户信息列表")
    @PostMapping("/list")
    @RequiresPermissions("system:user:query")
    public AjaxResult<Object> getList(@RequestBody @Validated QueryUserVo form) {
        QueryUserContext context = translate.toQueryUserVoByQueryUserContext(form);
        DDDContext<QueryUserContext, PageVO<UserTableContext>> userList = userService.queryUserManageList(DDDContext.request(context));
        if (userList.isError()) {
            return AjaxResult.error(userList.getMsg());
        }
        return AjaxResult.success(userList.getResponse().getData());
    }

    @WebLog("添加用户信息")
    @PostMapping("/add")
    @RepeatSubmit
    @RequiresPermissions("system:user:add")
    public AjaxResult<Object> addUser(@RequestBody @Validated AddUserVo form) {
        UserContext userContext = translate.toUserContextByAddUserVo(form);
        DDDContext<Void, Void> response = userService.creatUser(DDDContext.request(userContext));
        if (response.isError()) {
            return AjaxResult.error(response.getMsg());
        }
        return AjaxResult.success(true);
    }

    @WebLog("更新用户信息")
    @PutMapping("/update")
    @RepeatSubmit
    @RequiresPermissions("system:user:edit")
    public AjaxResult<Object> updateUser(@RequestBody @Validated UpdateUserVo form) {
        UserContext userContext = translate.toUserContextByUpdateUserVo(form);
        DDDContext<Void, Void> response = userService.updateUser(DDDContext.request(userContext));
        if (response.isError()) {
            return AjaxResult.error(response.getMsg());
        }
        return AjaxResult.success(true);
    }


    /**
     * 更新用户状态
     */
    @WebLog("更新用户状态")
    @PostMapping("/state")
    @RequiresPermissions("system:user:status")
    public AjaxResult<Object> updateStatus(@RequestBody @Validated IdVo form) {
        UserContext uc = translate.toUserContextByIdVo(form);
        DDDContext<Void, Boolean> resContext = userService.updateStatus(DDDContext.request(uc));
        if (resContext.isError()) {
            return AjaxResult.error(resContext.getMsg());
        }
        return AjaxResult.success(resContext.getResponse().getData());
    }


    /**
     * 删除用户信息
     *
     * @param form id表单
     * @return 返回结果
     */
    @WebLog("删除用户信息")
    @DeleteMapping("/delete")
    @RepeatSubmit
    @RequiresPermissions("system:user:del")
    public AjaxResult<Object> deleteUser(@RequestBody @Validated IdVo form) {
        UserContext uc = translate.toUserContextByIdVo(form);
        DDDContext<Void, Boolean> resContext = userService.deleteUser(DDDContext.request(uc));
        if (resContext.isError()) {
            return AjaxResult.error(resContext.getMsg());
        }
        return AjaxResult.success(resContext.getResponse().getData());
    }


    /**
     * 通过用户Id获取用户详细详细
     *
     * @param form id表单
     * @return 返回结果
     */
    @WebLog("获取用户详细信息")
    @PostMapping("/getUserInfo")
    public AjaxResult<Object> getUserInfoById(@RequestBody @Validated IdVo form) {
        UserContext userContext = translate.toUserContextByIdVo(form);
        DDDContext<Void, UserContext> rc = userService.getUserInfoById(DDDContext.request(userContext));
        if (rc.isError()) {
            return AjaxResult.error(rc.getMsg());
        }
        UpdateUserVo uuv = translate.toUpdateUSerVoByUserContext(rc.getResponse().getData());
        uuv.setPassWord(null);
        return AjaxResult.success(uuv);
    }


    /**
     * 获取登录用户信息
     *
     * @return 返回当前登录的用户信息
     */
    @WebLog("获取登录用户信息")
    @PostMapping("/getLoginUserInfo")
    public AjaxResult<Object> getLoginUserInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (Objects.isNull(loginUser)) {
            AjaxResult.error("获取用户信息异常");
        }
        return AjaxResult.success(loginUser);
    }

    /**
     * 重置用户密码
     *
     * @param form id表单
     * @return 返回结果
     */
    @WebLog("重置用户密码")
    @PostMapping("/resetPassWord")
    @RequiresPermissions("system:user:resetPwd")
    public AjaxResult<Object> resetPassWord(@RequestBody @Validated ResetPassWordVo form) {
        ResetPassContext a = translate.toResetPassContextByResetPassWordVo(form);
        DDDContext<Void, Boolean> resetPassword = userService.resetPassword(DDDContext.request(a));
        if (resetPassword.isError()) {
            return AjaxResult.error(resetPassword.getMsg());
        }
        return AjaxResult.success(resetPassword.getResponse().getData());
    }


    /**
     * 获取登录用户可选角色列表
     *
     * @return 返回角色列表
     */
    @WebLog("获取登录用户可选角色列表")
    @GetMapping("/getLoginUserRole")
    public AjaxResult<Object> getLoginUserRole() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        LoginUserContext loginUserContext = loginControllerTranslate.toLoginUserContextByLoginUser(loginUser);
        DDDContext<Void, List<RoleContext>> ctx = userService.getLoginUserRole(DDDContext.request(loginUserContext));
        if (ctx.isError()) {
            return AjaxResult.error(ctx.getMsg());
        }
        List<RoleVO> response = translate.toRoleVOListByRoleContextList(ctx.getResponse().getData());
        return AjaxResult.success(response);
    }

}
