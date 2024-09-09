package io.github.kamarias.dbf.system.translate;

import io.github.kamarias.dbf.system.dto.RoleDto;
import io.github.kamarias.dbf.system.model.RoleOptionsModel;
import io.github.kamarias.dbf.system.model.RoleTableModel;
import io.github.kamarias.vo.PageVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleDomainTranslate {


    List<RoleOptionsModel> toRoleOptionsModelListByRoleDtoList(List<RoleDto> allRole);


    PageVO<RoleTableModel> toRoleTableModelPageVOByRoleDtoPageVO(PageVO<RoleDto> a);
}
