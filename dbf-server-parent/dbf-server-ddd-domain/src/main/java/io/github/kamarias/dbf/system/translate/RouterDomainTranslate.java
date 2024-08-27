package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.dto.RouterDto;
import io.github.kamarias.dbf.system.dto.RouterMetaDto;
import io.github.kamarias.dbf.system.model.MenuRouterMetaModel;
import io.github.kamarias.dbf.system.model.MenuRouterModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouterDomainTranslate {


    MenuRouterModel toMenuRouterModelByRouterDto(RouterDto routerDto);


    MenuRouterMetaModel toMenuRouterMetaModelByRouterMetaDto(RouterMetaDto routerMeta);


}
