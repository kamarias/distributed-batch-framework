package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.dto.RouterMetaDto;
import io.github.kamarias.dbf.system.entity.RouterMetaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouterMetaStoreTranslate {


    RouterMetaDto toRouterMetaDtoByRouterMetaEntity(RouterMetaEntity routerMeta);


}
