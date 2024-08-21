package com.borathings.borapagar.user;

import com.borathings.borapagar.user.dto.UserDTO;
import org.springframework.stereotype.Component;

/** UserMapper */
@Component
public class UserMapper {
    public static UserDTO toDTO(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getName(),
                userEntity.getImageUrl());
    }
}
