package com.borathings.borapagar.course;

import com.borathings.borapagar.core.AbstractModel;
import com.borathings.borapagar.course.subject.SubjectCourseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CourseEntity extends AbstractModel {
    @Column private String name;
    @Column private String coordinator;

    @OneToMany(mappedBy = "course")
    private List<SubjectCourseEntity> subjects;
}
