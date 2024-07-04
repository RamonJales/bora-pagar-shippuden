package com.borathings.borapagar.subject.subject_course;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

@Embeddable
@Data
public class SubjectCourseKey implements Serializable {
    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "course_id")
    private Long courseId;
}
