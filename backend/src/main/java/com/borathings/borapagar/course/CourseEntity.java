package com.borathings.borapagar.course;

import com.borathings.borapagar.core.AbstractModel;
import com.borathings.borapagar.course.enumTypes.CourseLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CourseEntity extends AbstractModel{
    @Column
    private String name;

    @Column
    private CourseLevel courseLevel;

    @Column
    private String coordinator;
}
