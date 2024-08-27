package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.dto.RouterDto;
import io.github.kamarias.dbf.system.entity.RouterEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouterStoreTranslate {


    List<RouterDto> toRouterDtoListByRouterEntityList(List<RouterEntity> routers);


}
