package io.github.kamarias.dbf.system.gateway;

import io.github.kamarias.dbf.system.dto.RoleDto;

import java.util.List;

public interface RoleStoreGateway {


    RoleDto randomFindRoleByUserId(String userId);


    RoleDto findRoleByUserIdAndRoleId(String userId, String roleId);

    List<RoleDto> getAllRole();
}
