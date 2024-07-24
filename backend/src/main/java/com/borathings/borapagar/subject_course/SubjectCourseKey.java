package com.borathings.borapagar.subject_course;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectCourseKey implements Serializable {
    private Long subjectId;
    private Long courseId;
}
