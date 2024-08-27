package io.github.kamarias.dbf.system.domain;

import com.alibaba.fastjson2.JSON;
import io.github.kamarias.dbf.enums.BoolFlagEnum;
import io.github.kamarias.dbf.system.dto.RouterDto;
import io.github.kamarias.dbf.system.dto.RouterMetaDto;
import io.github.kamarias.dbf.system.enums.RouterTypeEnum;
import io.github.kamarias.dbf.system.gateway.RouterMetaStoreGateway;
import io.github.kamarias.dbf.system.gateway.RouterRoleStoreGateway;
import io.github.kamarias.dbf.system.gateway.RouterStoreGateway;
import io.github.kamarias.dbf.system.model.MenuRouterMetaModel;
import io.github.kamarias.dbf.system.model.MenuRouterModel;
import io.github.kamarias.dbf.system.model.RouterTransitionModel;
import io.github.kamarias.dbf.system.translate.RouterDomainTranslate;
import io.github.kamarias.dto.DDDContext;
import io.github.kamarias.utils.string.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouterDomainService {

    private final RouterStoreGateway routerStoreGateway;

    private final RouterMetaStoreGateway routerMetaStoreGateway;

    private final RouterRoleStoreGateway routerRoleStoreGateway;

    private final RouterDomainTranslate routerDomainTranslate;


    public RouterDomainService(RouterStoreGateway routerStoreGateway, RouterMetaStoreGateway routerMetaStoreGateway, RouterRoleStoreGateway routerRoleStoreGateway, RouterDomainTranslate routerDomainTranslate) {
        this.routerStoreGateway = routerStoreGateway;
        this.routerMetaStoreGateway = routerMetaStoreGateway;
        this.routerRoleStoreGateway = routerRoleStoreGateway;
        this.routerDomainTranslate = routerDomainTranslate;
    }


    public DDDContext<Void, List<MenuRouterModel>> generateMenuRouteTree() {
        List<RouterDto> routerDtoList = routerStoreGateway.findAllRouterByStatus(BoolFlagEnum.YES.getCode());

        List<MenuRouterModel> treeMenuRouter;
        try {
            treeMenuRouter = tree("0", routerDtoList);
        } catch (IllegalArgumentException exception) {
            return DDDContext.error(exception.getMessage());
        }
        return DDDContext.success(treeMenuRouter);
    }


    private List<MenuRouterModel> tree(String parentRouterId, List<RouterDto> routerDtoList) {
        return routerDtoList.stream().filter(x -> parentRouterId.equals(x.getParentRouterId())).map(x -> {
            MenuRouterModel menuRouter = routerDomainTranslate.toMenuRouterModelByRouterDto(x);
            RouterMetaDto routerMeta = routerMetaStoreGateway.getRouterMetaByRouterId(x.getId());
            Assert.notNull(routerMeta, "路由元数据异常");

            MenuRouterMetaModel routerMetaVO = routerDomainTranslate.toMenuRouterMetaModelByRouterMetaDto(routerMeta);
            if (RouterTypeEnum.DIRECTORY.getKey().equals(x.getType())) {
                // 目录需要 将roles 置为null
                routerMetaVO.setRoles(null);
                MenuRouterMetaModel vo = new MenuRouterMetaModel();
                vo.setIcon(routerMetaVO.getIcon());
                vo.setTitle(routerMetaVO.getTitle());
                vo.setRank(routerMetaVO.getRank());
                menuRouter.setMeta(vo);
            } else {
                // 非目录的路由菜单需要设置权限，路由动画
                routerMetaVO.setTransition(getRouterTransitionVOByJson(routerMeta.getTransition()));
                List<String> roles = routerRoleStoreGateway.getRoleCodeByRouterId(routerMeta.getRouterId());
                routerMetaVO.setRoles(roles);
                menuRouter.setMeta(routerMetaVO);
            }
            menuRouter.setChildren(tree(x.getId(), routerDtoList));
            return menuRouter;
        }).sorted(Comparator.comparing(x -> x.getMeta().getRank())).collect(Collectors.toList());
    }


    private RouterTransitionModel getRouterTransitionVOByJson(String transition) {
        RouterTransitionModel vo = JSON.parseObject(transition, RouterTransitionModel.class);
        // 动画配置一个属性不为null才返回
        if (StringUtils.isNotBlank(vo.getName()) ||
                StringUtils.isNotBlank(vo.getEnterTransition()) ||
                StringUtils.isNotBlank(vo.getLeaveTransition())) {
            return vo;
        }
        return null;
    }

}
