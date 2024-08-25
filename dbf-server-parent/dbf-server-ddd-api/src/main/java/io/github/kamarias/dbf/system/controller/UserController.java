package io.github.kamarias.dbf.system.controller;


import io.github.kamarias.annotation.RequiresPermissions;
import io.github.kamarias.dbf.system.context.UserContext;
import io.github.kamarias.dbf.system.service.UserService;
import io.github.kamarias.dbf.system.translate.UserControllerTranslate;
import io.github.kamarias.dbf.system.vo.AddUserVo;
import io.github.kamarias.dto.AjaxResult;
import io.github.kamarias.dto.ContextResponse;
import io.github.kamarias.web.annotation.RepeatSubmit;
import io.github.kamarias.web.annotation.WebLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 10:13
 */
@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    private final UserControllerTranslate translate;


    public UserController(UserService userService, UserControllerTranslate translate) {
        this.userService = userService;
        this.translate = translate;
    }


//    /**
//     * 查询用户信息列表
//     */
//    @WebLog("查询用户信息列表")
//    @PostMapping("/list")
//    @RequiresPermissions("system:user:query")
//    public AjaxResult<PageVO<UserEntity>> getList(@RequestBody @Validated QueryUserForm form) {
//        return AjaxResult.success(userService.getList(form));
//    }

    @WebLog("添加用户信息")
    @PostMapping("/add")
    @RepeatSubmit
    @RequiresPermissions("system:user:add")
    public AjaxResult<Object> addUser(@RequestBody @Validated AddUserVo form) {
        UserContext userContext = translate.toUserContextByAddUserVo(form);
        ContextResponse<Void> response = userService.creatUser(userContext);
        if (response.isError()) {
            return AjaxResult.error(response.getMsg());
        }
        return AjaxResult.success(true);
    }

//    @WebLog("更新用户信息")
//    @PutMapping("/update")
//    @RepeatSubmit
//    @RequiresPermissions("system:user:edit")
//    public AjaxResult<Boolean> updateUser(@RequestBody @Validated UpdateUserForm form) {
//        return AjaxResult.success(userService.updateUser(form));
//    }


//    /**
//     * 更新用户状态
//     */
//    @WebLog("更新用户状态")
//    @PostMapping("/state")
//    @RequiresPermissions("system:user:status")
//    public AjaxResult<Boolean> updateStatus(@RequestBody @Validated IdForm form) {
//        return AjaxResult.success(userService.updateStatus(form));
//    }


//    /**
//     * 删除用户信息
//     *
//     * @param form id表单
//     * @return 返回结果
//     */
//    @WebLog("删除用户信息")
//    @DeleteMapping("/delete")
//    @RepeatSubmit
//    @RequiresPermissions("system:user:del")
//    public AjaxResult<Boolean> deleteUser(@RequestBody @Validated IdForm form) {
//        return AjaxResult.success(userService.deleteUser(form));
//    }


//    /**
//     * 通过用户Id获取用户详细详细
//     *
//     * @param form id表单
//     * @return 返回结果
//     */
//    @WebLog("获取用户详细信息")
//    @PostMapping("/getUserInfo")
//    public AjaxResult<UpdateUserForm> getUserInfoById(@RequestBody @Validated IdForm form) {
//        return AjaxResult.success(userService.getUserInfoById(form));
//    }


//    /**
//     * 获取登录用户信息
//     *
//     * @return 返回当前登录的用户信息
//     */
//    @WebLog("获取登录用户信息")
//    @PostMapping("/getLoginUserInfo")
//    public <T extends LoginObject> AjaxResult<T> getLoginUserInfo() {
//        return AjaxResult.success(userService.getLoginUser());
//    }
//
//    /**
//     * 重置用户密码
//     *
//     * @param form id表单
//     * @return 返回结果
//     */
//    @WebLog("重置用户密码")
//    @PostMapping("/resetPassWord")
//    @RequiresPermissions("system:user:resetPwd")
//    public AjaxResult<Boolean> resetPassWord(@RequestBody @Validated ResetPassWordForm form) {
//        return AjaxResult.success(userService.resetPassword(form.getId(), form.getPassWord()));
//    }
//
//    /**
//     * 获取登录用户可选角色列表
//     *
//     * @return 返回角色列表
//     */
//    @WebLog("获取登录用户可选角色列表")
//    @GetMapping("/getLoginUserRole")
//    public AjaxResult<List<RoleOptionsVO>> getLoginUserRole() {
//        return AjaxResult.success(userService.getLoginUserRole());
//    }

}
