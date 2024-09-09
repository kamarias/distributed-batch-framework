package io.github.kamarias.dbf.system.gateway;

import io.github.kamarias.dbf.system.dto.RoleDto;
import io.github.kamarias.dbf.system.model.QueryRoleModel;
import io.github.kamarias.vo.PageVO;

import java.util.List;

public interface RoleStoreGateway {


    RoleDto randomFindRoleByUserId(String userId);


    RoleDto findRoleByUserIdAndRoleId(String userId, String roleId);

    List<RoleDto> getAllRole();

    PageVO<RoleDto> queryRoleTableList(QueryRoleModel qum);
}
