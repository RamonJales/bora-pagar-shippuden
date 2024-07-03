package com.borathings.borapagar.subject;

import java.util.List;

import com.borathings.borapagar.core.AbstractModel;
import com.borathings.borapagar.subject.subject_course.SubjectCourseRelationship;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subject")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SubjectEntity extends AbstractModel {
    @Column private String name;
    @Column private String code;
    @Column @Nullable private String program;
    @Column private Integer hours;

    @OneToMany(mappedBy = "subject")
    private List<SubjectCourseRelationship> courses;
}
