package com.borathings.borapagar.subject_course.dto;

import com.borathings.borapagar.subject_course.SubjectCourseEntity;
import com.borathings.borapagar.subject_course.enumTypes.SubjectCourseType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSubjectCourseRequest {

    @Min(1)
    private Integer expectedSemester;

    // TODO: implementar validação de enum
    @NotNull private SubjectCourseType subjectCourseType;

    public SubjectCourseEntity toEntity() {
        SubjectCourseEntity subjectCourseEntity = new SubjectCourseEntity();

        subjectCourseEntity.setExpectedSemester(this.expectedSemester);
        subjectCourseEntity.setSubjectCourseType(this.subjectCourseType);
        return subjectCourseEntity;
    }
}
