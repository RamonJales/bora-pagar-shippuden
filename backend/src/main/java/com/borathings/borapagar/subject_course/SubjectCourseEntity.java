package com.borathings.borapagar.subject_course;

import com.borathings.borapagar.course.CourseEntity;
import com.borathings.borapagar.subject.SubjectEntity;
import com.borathings.borapagar.subject_course.enumTypes.SubjectCourseType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subject_course")
public class SubjectCourseEntity {
    @EmbeddedId private SubjectCourseKey keyId;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    @JsonIgnore
    private SubjectEntity subject;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private CourseEntity course;

    @Nullable private Integer expectedSemester;

    @Enumerated(EnumType.STRING)
    private SubjectCourseType subjectCourseType;
}
