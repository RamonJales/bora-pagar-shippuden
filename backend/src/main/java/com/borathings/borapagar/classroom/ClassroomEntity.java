package com.borathings.borapagar.classroom;


import com.borathings.borapagar.core.AbstractModel;
import com.borathings.borapagar.subject.SubjectEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "classroom")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class ClassroomEntity extends AbstractModel {
    
    @ManyToOne
    @NotNull private SubjectEntity subject;

    @Column @NotNull private int places;
    
    @Column @NotNull private String location;

    @Column @NotNull private String yearPeriod;
}
