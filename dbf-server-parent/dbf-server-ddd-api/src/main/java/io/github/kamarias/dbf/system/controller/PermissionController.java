package io.github.kamarias.dbf.system.controller;


import org.springframework.web.bind.annotation.*;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/31 17:07
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
//
//    private final PermissionService permissionService;
//
//
//    public PermissionController(PermissionService permissionService) {
//        this.permissionService = permissionService;
//    }
//
//    /**
//     * 查询权限列表
//     */
//    @WebLog("查询权限列表")
//    @PostMapping("/list")
//    @RequiresPermissions("system:permission:query")
//    public AjaxResult<PageVO<PermissionEntity>> getList(@RequestBody QueryPermissionForm form) {
//        return AjaxResult.success(permissionService.getList(form));
//    }
//
//    /**
//     * 新增权限
//     */
//    @WebLog("新增权限")
//    @PostMapping("/add")
//    @RepeatSubmit
//    @RequiresPermissions("system:permission:add")
//    public AjaxResult<Boolean> addPermission(@RequestBody @Validated AddPermissionForm form) {
//        return AjaxResult.success(permissionService.addPermission(form));
//    }
//
//    /**
//     * 更新权限
//     */
//    @WebLog("更新权限")
//    @PutMapping("/update")
//    @RepeatSubmit
//    @RequiresPermissions("system:permission:edit")
//    public AjaxResult<Boolean> updatePermission(@RequestBody @Validated UpdatePermissionForm form) {
//        return AjaxResult.success(permissionService.updatePermission(form));
//    }
//
//    /**
//     * 删除权限
//     */
//    @WebLog("删除权限")
//    @DeleteMapping("/delete")
//    @RepeatSubmit
//    @RequiresPermissions("system:permission:del")
//    public AjaxResult<Boolean> deletePermission(@RequestBody @Validated IdForm form) {
//        return AjaxResult.success(permissionService.deletePermission(form));
//    }
//
//    @WebLog("获取所有权限列表")
//    @PostMapping("/listAll")
//    public AjaxResult<List<PermissionEntity>> getListAll() {
//        return AjaxResult.success(permissionService.getListAll());
//    }

}
