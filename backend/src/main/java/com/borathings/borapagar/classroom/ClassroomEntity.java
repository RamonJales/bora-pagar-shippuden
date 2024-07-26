package com.borathings.borapagar.classroom;

import javax.security.auth.Subject;

import com.borathings.borapagar.core.AbstractModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class ClassroomEntity extends AbstractModel {
    
    @ManyToOne
    @Column @NotNull private Subject subject;

    @Column @NotNull private int places;
    
    @Column @NotNull private String location;

    @Column @NotNull private String yearPeriod;
}
