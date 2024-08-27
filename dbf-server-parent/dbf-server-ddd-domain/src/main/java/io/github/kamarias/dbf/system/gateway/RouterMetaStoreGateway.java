package io.github.kamarias.dbf.system.gateway;

import io.github.kamarias.dbf.system.dto.RouterMetaDto;

public interface RouterMetaStoreGateway {


    RouterMetaDto getRouterMetaByRouterId(String routerId);


}
