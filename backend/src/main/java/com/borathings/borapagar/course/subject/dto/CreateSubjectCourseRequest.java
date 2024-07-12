package com.borathings.borapagar.course.subject.dto;

import com.borathings.borapagar.course.subject.SubjectCourseEntity;
import com.borathings.borapagar.course.subject.SubjectCourseKey;
import com.borathings.borapagar.course.subject.enumTypes.SubjectCourseType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSubjectCourseRequest {
    @NotNull private Long subjectId;

    @Min(1)
    private Integer expectedSemester;

    // TODO: implementar validação de enum
    @NotNull private SubjectCourseType subjectCourseType;

    public SubjectCourseEntity toEntity() {
        SubjectCourseEntity subjectCourseEntity = new SubjectCourseEntity();

        subjectCourseEntity.setKeyId(new SubjectCourseKey(subjectId, null));
        subjectCourseEntity.setExpectedSemester(this.expectedSemester);
        subjectCourseEntity.setSubjectCourseType(this.subjectCourseType);
        return subjectCourseEntity;
    }
}
