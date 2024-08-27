package io.github.kamarias.dbf.system.service;

import io.github.kamarias.dbf.system.context.LoginUserContext;
import io.github.kamarias.dbf.system.context.MenuRouterContext;
import io.github.kamarias.dbf.system.domain.RouterDomainService;
import io.github.kamarias.dbf.system.model.MenuRouterModel;
import io.github.kamarias.dbf.system.translate.RouterServiceTranslate;
import io.github.kamarias.dto.DDDContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouterService {


    private final RouterDomainService routerDomain;

    private final RouterServiceTranslate translate;

    public RouterService(RouterDomainService routerDomain, RouterServiceTranslate translate) {
        this.routerDomain = routerDomain;
        this.translate = translate;
    }


    public DDDContext<Void, List<MenuRouterContext>> getMenuRouters() {
        DDDContext<Void, List<MenuRouterModel>> ctx = routerDomain.generateMenuRouteTree();
        if (ctx.isError()) {
            return DDDContext.error(ctx.getMsg());
        }
        List<MenuRouterContext> allMenus = translate.MenuRouterContextListByMenuRouterModelList(ctx.getResponse().getData());
        return DDDContext.success(allMenus);
    }
}
