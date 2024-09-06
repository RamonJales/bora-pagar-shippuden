package com.borathings.borapagar.subject.dto.response;

public record SubjectResponseDTO(
        Long id, String name, String code, String syllabus, Integer hours, String departmentName) {}
;
