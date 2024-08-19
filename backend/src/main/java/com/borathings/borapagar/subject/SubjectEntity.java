package com.borathings.borapagar.subject;

import com.borathings.borapagar.classroom.ClassroomEntity;
import com.borathings.borapagar.core.SoftDeletableModel;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "subject")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class SubjectEntity extends SoftDeletableModel {
    @Column @NotNull private String name;

    @Column @NotNull private String code;

    @Column @Nullable private String syllabus;

    @Column @NotNull private Integer hours;

    @OneToMany(mappedBy = "subject")
    private Set<ClassroomEntity> classrooms;
}
