package com.borathings.borapagar.user;

import com.borathings.borapagar.user.dto.response.DefaultUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/** UserMapper */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    public DefaultUserDTO userToDefaultUserDTO(UserEntity userEntity);
}
