package io.github.kamarias.dbf.system.service;

import io.github.kamarias.dbf.system.context.QueryRoleContext;
import io.github.kamarias.dbf.system.context.QueryUserContext;
import io.github.kamarias.dbf.system.context.RoleTableContext;
import io.github.kamarias.dbf.system.context.UserTableContext;
import io.github.kamarias.dbf.system.domain.RoleDomainService;
import io.github.kamarias.dbf.system.model.*;
import io.github.kamarias.dbf.system.translate.RoleServiceTranslate;
import io.github.kamarias.dto.DDDContext;
import io.github.kamarias.vo.PageVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleDomainService roleDomain;

    private final RoleServiceTranslate translate;

    public RoleService(RoleDomainService roleDomain, RoleServiceTranslate translate) {
        this.roleDomain = roleDomain;
        this.translate = translate;
    }

    public DDDContext<Void, List<RoleOptionsModel>> getRoleOptions() {
        DDDContext<Void, List<RoleOptionsModel>> context = roleDomain.getRoleOptions();
        if (context.isError()) {
            return DDDContext.error(context.getMsg());
        }
        return context;
    }


    public DDDContext<QueryUserContext, PageVO<RoleTableContext>> queryRoleManageList(DDDContext<QueryRoleContext, Void> request) {
        QueryRoleContext roleContext = request.getRequest().getData();
        QueryRoleModel qum = translate.toQueryUserModelByQueryUserContext(roleContext);
        DDDContext<QueryUserModel, PageVO<RoleTableModel>> model = roleDomain.queryRoleTableList(qum);
        if (model.isError()) {
            return DDDContext.error(model.getMsg());
        }
        return DDDContext.success(translate.toRoleTableContextPageVOByRoleTableModelPageVO(model.getResponse().getData()));


    }
}
