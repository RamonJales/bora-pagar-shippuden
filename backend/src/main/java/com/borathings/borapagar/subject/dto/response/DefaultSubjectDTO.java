package com.borathings.borapagar.subject.dto.response;

public record DefaultSubjectDTO(
        Long id, String name, String code, String syllabus, Integer hours, String departmentName) {}
;
