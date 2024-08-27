package io.github.kamarias.dbf.system.gateway;

import java.util.Set;

public interface PermissionStoreGateway {
    Set<String> getPermissionCodeByRoleId(String roleId);
}
