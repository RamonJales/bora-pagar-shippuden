package com.borathings.borapagar.subject_course.dto;

import com.borathings.borapagar.subject_course.SubjectCourseEntity;
import com.borathings.borapagar.subject_course.SubjectCourseKey;
import com.borathings.borapagar.subject_course.enumTypes.SubjectCourseType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSubjectCourseRequest {
    @NotNull private Long subjectId;

    @Min(1)
    private Integer expectedSemester;

    // TODO: implementar validação de enum
    @NotNull private SubjectCourseType subjectCourseType;

    public SubjectCourseEntity toEntity() {
        SubjectCourseKey subjectCourseKey = SubjectCourseKey.builder().subjectId(subjectId).build();
        SubjectCourseEntity subjectCourseEntity =
                SubjectCourseEntity.builder()
                        .keyId(subjectCourseKey)
                        .expectedSemester(expectedSemester)
                        .subjectCourseType(subjectCourseType)
                        .build();
        return subjectCourseEntity;
    }
}
