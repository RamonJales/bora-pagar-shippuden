package com.borathings.borapagar.subject_course;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
public class SubjectCourseKey implements Serializable {
    private Long subjectId;
    private Long courseId;
}
