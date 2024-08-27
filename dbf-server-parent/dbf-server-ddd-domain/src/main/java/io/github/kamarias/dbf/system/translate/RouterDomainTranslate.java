package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.dto.RouterDto;
import io.github.kamarias.dbf.system.dto.RouterMetaDto;
import io.github.kamarias.dbf.system.model.MenuRouterMetaModel;
import io.github.kamarias.dbf.system.model.MenuRouterModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RouterDomainTranslate {


    MenuRouterModel toMenuRouterModelByRouterDto(RouterDto routerDto);

    @Mappings({
            @Mapping(target = "transition", ignore = true)
    })
    MenuRouterMetaModel toMenuRouterMetaModelByRouterMetaDto(RouterMetaDto routerMeta);


}
