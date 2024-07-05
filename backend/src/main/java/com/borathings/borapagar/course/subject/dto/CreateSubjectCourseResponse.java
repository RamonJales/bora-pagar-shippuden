package com.borathings.borapagar.course.subject.dto;

import com.borathings.borapagar.course.subject.SubjectCourseEntity;
import com.borathings.borapagar.course.subject.enumTypes.SubjectCourseType;

public class CreateSubjectCourseResponse {
    public Long courseId;
    public Long subjectId;
    public Integer level;
    public SubjectCourseType type;

    public static CreateSubjectCourseResponse fromEntity(SubjectCourseEntity entity) {
        CreateSubjectCourseResponse dto = new CreateSubjectCourseResponse();
        dto.courseId = entity.getKeyId().getCourseId();
        dto.subjectId = entity.getKeyId().getSubjectId();
        dto.level = entity.getLevel();
        dto.type = entity.getSubjectCourseType();
        return dto;
    }
}
