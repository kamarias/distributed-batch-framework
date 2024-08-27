package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.context.MenuRouterContext;
import io.github.kamarias.dbf.system.vo.MenuRouterVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouterControllerTranslate {


    List<MenuRouterVO> toLoginUserRouterVOListByLoginUserRouterContextList(List<MenuRouterContext> data);


}
