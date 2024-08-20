package com.borathings.borapagar.classroom;

import com.borathings.borapagar.core.AbstractModel;
import com.borathings.borapagar.subject.SubjectEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "classroom")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class ClassroomEntity extends AbstractModel {

    @ManyToOne @NotNull private SubjectEntity subject;

    @Column @NotNull private int seats;

    @Column @NotNull private String location;

    @Column @NotNull private String yearPeriod;
}
