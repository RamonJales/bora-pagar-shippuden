package com.borathings.borapagar.course;

import com.borathings.borapagar.core.AbstractModel;
import com.borathings.borapagar.course.enumTypes.CourseLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
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

    @Column
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;

    @Column private String coordinator;
}
