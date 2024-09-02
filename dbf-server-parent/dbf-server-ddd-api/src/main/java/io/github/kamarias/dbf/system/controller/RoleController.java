package io.github.kamarias.dbf.system.controller;


import io.github.kamarias.dbf.system.model.RoleOptionsModel;
import io.github.kamarias.dbf.system.service.RoleService;
import io.github.kamarias.dto.AjaxResult;
import io.github.kamarias.dto.DDDContext;
import io.github.kamarias.web.annotation.WebLog;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/30 14:24
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

//    /**
//     * 查询角色列表
//     */
//    @WebLog("查询角色列表")
//    @PostMapping("/list")
//    @RequiresPermissions("system:role:query")
//    public AjaxResult getList(@RequestBody QueryRoleForm form) {
//        return AjaxResult.success(roleService.getList(form));
//    }
//
//    /**
//     * 更新角色状态
//     */
//    @WebLog("更新角色状态")
//    @PostMapping("/status")
//    @RequiresPermissions("system:role:status")
//    public AjaxResult updateStatus(@RequestBody @Validated IdForm form) {
//        return AjaxResult.success(roleService.updateStatus(form));
//    }
//
//    /**
//     * 新增角色
//     */
//    @WebLog("新增角色")
//    @PostMapping("/add")
//    @RepeatSubmit
//    @RequiresPermissions("system:role:add")
//    public AjaxResult addRole(@RequestBody @Validated AddRoleForm form) {
//        return AjaxResult.success(roleService.addRole(form));
//    }
//
//    /**
//     * 更新角色
//     */
//    @WebLog("更新角色")
//    @PutMapping("/update")
//    @RepeatSubmit
//    @RequiresPermissions("system:role:edit")
//    public AjaxResult updateRole(@RequestBody @Validated UpdateRoleForm form) {
//        return AjaxResult.success(roleService.updateRole(form));
//    }
//
//    /**
//     * 删除角色
//     */
//    @WebLog("删除角色")
//    @DeleteMapping("/delete")
//    @RepeatSubmit
//    @RequiresPermissions("system:role:del")
//    public AjaxResult delete(@RequestBody @Validated IdForm form) {
//        return AjaxResult.success(roleService.deleteRole(form));
//    }
//
//
//    /**
//     * 获取角色详情信息
//     */
//    @WebLog("获取角色详情信息")
//    @PostMapping("/detail")
//    public AjaxResult detailById(@RequestBody @Validated IdForm form) {
//        return AjaxResult.success(roleService.detailById(form));
//    }

    /**
     * 获取角色可选选项
     */
    @WebLog("获取角色可选选项")
    @GetMapping("/options")
    public AjaxResult<Object> options() {
        DDDContext<Void, List<RoleOptionsModel>> context = roleService.getRoleOptions();
        if (context.isError()){
            return AjaxResult.error(context.getMsg());
        }
        return AjaxResult.success(context.getResponse().getData());
    }

}
