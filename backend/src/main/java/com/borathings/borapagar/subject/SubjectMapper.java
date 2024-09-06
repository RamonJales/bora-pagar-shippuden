package com.borathings.borapagar.subject;

import com.borathings.borapagar.subject.dto.response.SubjectResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubjectMapper {

    @Mapping(target = "departmentName", source = "department.name")
    public SubjectResponseDTO toSubjectResponseDTO(SubjectEntity subject);
}
