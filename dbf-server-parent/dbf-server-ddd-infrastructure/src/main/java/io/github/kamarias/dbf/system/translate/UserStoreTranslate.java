package io.github.kamarias.dbf.system.translate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.kamarias.dbf.system.dto.UserDto;
import io.github.kamarias.dbf.system.entity.UserEntity;
import io.github.kamarias.vo.PageVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserStoreTranslate {


    UserDto toUserDtoByUserEntity(UserEntity userEntity);


    UserEntity toUserEntityByUserDto(UserDto userDto);

    Page<UserDto> toPageUserDtoByUserEntityPage(Page<UserEntity> userPage);
}
