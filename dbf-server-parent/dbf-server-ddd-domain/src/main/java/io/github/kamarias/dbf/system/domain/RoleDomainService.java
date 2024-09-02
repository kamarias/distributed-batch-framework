package io.github.kamarias.dbf.system.domain;

import io.github.kamarias.dbf.system.dto.RoleDto;
import io.github.kamarias.dbf.system.gateway.RoleStoreGateway;
import io.github.kamarias.dbf.system.model.RoleOptionsModel;
import io.github.kamarias.dbf.system.translate.RoleDomainTranslate;
import io.github.kamarias.dbf.system.translate.UserDomainTranslate;
import io.github.kamarias.dto.AjaxResult;
import io.github.kamarias.dto.DDDContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoleDomainService {


    private final RoleStoreGateway roleStoreGateway;

    private final RoleDomainTranslate roleDomainTranslate;

    public RoleDomainService(RoleStoreGateway roleStoreGateway, RoleDomainTranslate roleDomainTranslate) {
        this.roleStoreGateway = roleStoreGateway;
        this.roleDomainTranslate = roleDomainTranslate;
    }


    public DDDContext<Void, List<RoleOptionsModel>> getRoleOptions() {
       List<RoleDto> allRole = roleStoreGateway.getAllRole();
       if (Objects.isNull(allRole)){
           return DDDContext.error("查询角色信息异常");
       }
       List<RoleOptionsModel> roleOptionsModels = roleDomainTranslate.toRoleOptionsModelListByRoleDtoList(allRole);
       return DDDContext.success(roleOptionsModels);
    }
}
