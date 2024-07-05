package com.borathings.borapagar.course.subject.dto;

import com.borathings.borapagar.course.subject.SubjectCourseEntity;
import com.borathings.borapagar.course.subject.enumTypes.SubjectCourseType;

public class GetSubjectCourseResponse {
    public Long id;
    public String name;
    public Integer level;
    public Integer hours;
    public SubjectCourseType type;
    public String code;

    public static GetSubjectCourseResponse fromEntity(SubjectCourseEntity entity) {
        GetSubjectCourseResponse dto = new GetSubjectCourseResponse();
        dto.id = entity.getKeyId().getSubjectId();
        dto.name = entity.getSubject().getName();
        dto.level = entity.getLevel();
        dto.hours = entity.getSubject().getHours();
        dto.type = entity.getSubjectCourseType();
        dto.code = entity.getSubject().getCode();
        return dto;
    }
}
