package com.borathings.borapagar.course;

import com.borathings.borapagar.core.AbstractModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "course")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class CourseEntity extends AbstractModel {
    @Column @NotNull private String name;

    @Column @NotNull private String coordinator;
}
