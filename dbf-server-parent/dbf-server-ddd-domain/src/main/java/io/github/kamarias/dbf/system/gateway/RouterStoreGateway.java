package io.github.kamarias.dbf.system.gateway;

import io.github.kamarias.dbf.system.dto.RouterDto;

import java.util.List;

public interface RouterStoreGateway {


    /**
     * 根据状态查询路由
     * @param status 状态
     * @return 路由集合
     */
    List<RouterDto> findAllRouterByStatus(Integer status);


}
