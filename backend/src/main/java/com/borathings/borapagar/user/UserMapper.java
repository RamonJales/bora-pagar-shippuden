package com.borathings.borapagar.user;

import com.borathings.borapagar.user.dto.response.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/** UserMapper */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    public UserResponseDTO toUserResponseDTO(UserEntity userEntity);
}
