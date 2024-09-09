package com.borathings.borapagar.professor;

import com.borathings.borapagar.core.SoftDeletableModel;
import com.borathings.borapagar.department.DepartmentEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.NaturalId;

/** ProfessorEntity */
@Entity(name = "professor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ProfessorEntity extends SoftDeletableModel {
    @Column @NotNull private String name;

    @Column @NotNull @NaturalId private String siapeCode;

    @Column private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;
}
