package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.context.MenuRouterContext;
import io.github.kamarias.dbf.system.model.MenuRouterModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouterServiceTranslate {


    List<MenuRouterContext> MenuRouterContextListByMenuRouterModelList(List<MenuRouterModel> data);


}
