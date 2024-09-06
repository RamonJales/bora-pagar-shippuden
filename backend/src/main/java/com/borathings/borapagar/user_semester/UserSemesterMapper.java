package com.borathings.borapagar.user_semester;

import com.borathings.borapagar.user_semester.dto.response.DefaultUserSemesterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserSemesterMapper {
    public DefaultUserSemesterDTO userSemesterToDefaultUserSemesterDTO(
            UserSemesterEntity userSemester);
}
