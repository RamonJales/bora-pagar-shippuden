package com.borathings.borapagar.classroom;

import com.borathings.borapagar.core.SoftDeletableModel;
import com.borathings.borapagar.subject.SubjectEntity;
import com.borathings.borapagar.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
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
public class ClassroomEntity extends SoftDeletableModel {

    @ManyToOne @NotNull private SubjectEntity subject;

    @Column @NotNull private int seats;

    @Column @NotNull private String location;

    @Column @NotNull private String yearPeriod;

    @ManyToMany
    @JoinTable(
            name = "enrollment",
            joinColumns = @JoinColumn(name = "classroom_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> enrolledUsers;
}
