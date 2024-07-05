package com.borathings.borapagar.course.subject.dto;

import com.borathings.borapagar.course.subject.SubjectCourseEntity;
import com.borathings.borapagar.course.subject.enumTypes.SubjectCourseType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSubjectCourseRequest {

    private Integer level;

    // TODO: implementar validação de enum
    @NotNull private SubjectCourseType type;

    public SubjectCourseEntity toEntity() {
        SubjectCourseEntity subjectCourseEntity = new SubjectCourseEntity();

        subjectCourseEntity.setLevel(this.level);
        subjectCourseEntity.setSubjectCourseType(this.type);
        return subjectCourseEntity;
    }
}
