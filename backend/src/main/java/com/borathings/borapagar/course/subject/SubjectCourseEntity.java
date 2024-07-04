package com.borathings.borapagar.course.subject;

import com.borathings.borapagar.course.CourseEntity;
import com.borathings.borapagar.course.subject.enumTypes.SubjectCourseType;
import com.borathings.borapagar.subject.SubjectEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "subject_course")
public class SubjectCourseEntity {
    @EmbeddedId private SubjectCourseKey keyId;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @Nullable private Integer level;

    @Enumerated(EnumType.STRING)
    private SubjectCourseType subjectCourseType;
}
