package io.github.kamarias.dbf.system.gateway;

import io.github.kamarias.dbf.system.dto.RoleDto;

public interface RoleStoreGateway {


    RoleDto randomFindRoleByUserId(String userId);


    RoleDto findRoleByUserIdAndRoleId(String userId, String roleId);
}
