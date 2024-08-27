package io.github.kamarias.dbf.system.gateway;

import java.util.List;

public interface RouterRoleStoreGateway {


    /**
     * 通过路由Id 获取角色权限集合
     * @param routerId 路由Id
     * @return 返回结果
     */
    List<String> getRoleCodeByRouterId(String routerId);


}
