package com.borathings.borapagar.user_semester;

import com.borathings.borapagar.user_semester.dto.response.UserSemesterResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserSemesterMapper {
    public UserSemesterResponseDTO toUserSemesterResponseDTO(UserSemesterEntity userSemester);
}
