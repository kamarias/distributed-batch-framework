package io.github.kamarias.dbf.system.service;

import io.github.kamarias.dbf.system.domain.RoleDomainService;
import io.github.kamarias.dbf.system.model.RoleOptionsModel;
import io.github.kamarias.dto.DDDContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleDomainService roleDomain;

    public RoleService(RoleDomainService roleDomain) {
        this.roleDomain = roleDomain;
    }

    public DDDContext<Void, List<RoleOptionsModel>> getRoleOptions() {
        DDDContext<Void, List<RoleOptionsModel>> context = roleDomain.getRoleOptions();
        if (context.isError()) {
            return DDDContext.error(context.getMsg());
        }
        return context;
    }
}
