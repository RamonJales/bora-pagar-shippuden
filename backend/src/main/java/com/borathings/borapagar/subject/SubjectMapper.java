package com.borathings.borapagar.subject;

import com.borathings.borapagar.subject.dto.request.CreateSubjectDTO;
import com.borathings.borapagar.subject.dto.request.UpdateSubjectDTO;
import com.borathings.borapagar.subject.dto.response.SubjectResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubjectMapper {

    @Mapping(target = "departmentName", source = "department.name")
    public SubjectResponseDTO toSubjectResponseDTO(SubjectEntity subject);

    public SubjectEntity toEntity(CreateSubjectDTO createSubjectDTODTO);

    public SubjectEntity toEntity(UpdateSubjectDTO updateSubjectDTO);
}
