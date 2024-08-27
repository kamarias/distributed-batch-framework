package io.github.kamarias.dbf.system.controller;


import io.github.kamarias.dbf.system.context.LoginUserContext;
import io.github.kamarias.dbf.system.context.MenuRouterContext;
import io.github.kamarias.dbf.system.service.RouterService;
import io.github.kamarias.dbf.system.translate.LoginControllerTranslate;
import io.github.kamarias.dbf.system.translate.RouterControllerTranslate;
import io.github.kamarias.dbf.system.vo.MenuRouterVO;
import io.github.kamarias.dto.AjaxResult;
import io.github.kamarias.dto.DDDContext;
import io.github.kamarias.web.annotation.WebLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 14:21
 */
@RestController
@RequestMapping("/router")
public class RouterController {

    private final RouterService routerService;

    private final RouterControllerTranslate routerTranslate;


    private final LoginControllerTranslate loginTranslate;

    public RouterController(RouterService routerService, RouterControllerTranslate routerTranslate, LoginControllerTranslate loginTranslate) {
        this.routerService = routerService;
        this.routerTranslate = routerTranslate;
        this.loginTranslate = loginTranslate;
    }

    /**
     * 查询菜单路由树
     */
    @WebLog("查询菜单路由树")
    @GetMapping("/getRouters")
    public AjaxResult<Object> getMenuRouters() {
        DDDContext<Void, List<MenuRouterContext>> context = routerService.getMenuRouters();
        if (context.isError()) {
            return AjaxResult.error(context.getMsg());
        }
        List<MenuRouterVO> lu = routerTranslate.toLoginUserRouterVOListByLoginUserRouterContextList(context.getResponse().getData());
        return AjaxResult.success(lu);
    }

//
//    /**
//     * 获取路由管理树
//     */
//    @WebLog("获取路由管理树")
//    @GetMapping("/list")
//    @RequiresPermissions("system:router:query")
//    public AjaxResult<List<RouterManageVO>> getList() {
//        List<RouterManageVO> list = routerService.getRouterList();
//        return AjaxResult.success(list);
//    }
//
//
//    /**
//     * 获取路由选择树
//     */
//    @WebLog("获取路由选择树")
//    @GetMapping("/getRouterSelect")
//    public AjaxResult<List<RouterSelectVO>> getRouterSelect() {
//        List<RouterSelectVO> list = routerService.getRouterSelect();
//        return AjaxResult.success(list);
//    }
//
//
//    @WebLog("添加路由信息")
//    @PostMapping("/add")
//    @RepeatSubmit
//    @RequiresPermissions("system:router:add")
//    public AjaxResult<Boolean> addRouter(@RequestBody @Validated AddRouterForm form) {
//        return AjaxResult.success(routerService.addRouter(form));
//    }
//
//    @WebLog("更新路由信息")
//    @PutMapping("/update")
//    @RepeatSubmit
//    @RequiresPermissions("system:router:edit")
//    public AjaxResult<Boolean> updateRouter(@RequestBody @Validated UpdateRouterForm form) {
//        return AjaxResult.success(routerService.updateRouter(form));
//    }
//
//
//    @WebLog("删除路由信息")
//    @DeleteMapping("/delete")
//    @RepeatSubmit
//    @RequiresPermissions("system:router:del")
//    public AjaxResult<Boolean> deleteRouter(@RequestBody @Validated IdForm form) {
//        return AjaxResult.success(routerService.deleteRouter(form));
//    }
//
//    /**
//     * 更新路由状态
//     */
//    @WebLog("更新路由状态")
//    @PostMapping("/state")
//    @RequiresPermissions("system:router:status")
//    public AjaxResult updateStatus(@RequestBody @Validated IdForm form) {
//        return AjaxResult.success(routerService.updateStatus(form));
//    }
//
//    /**
//     * 获取路由详细信息
//     */
//    @WebLog("获取路由详细信息")
//    @PostMapping("/getRouterInfo")
//    public AjaxResult getRouterInfoById(@RequestBody @Validated IdForm form) {
//        return AjaxResult.success(routerService.getRouterInfoById(form));
//    }

}
