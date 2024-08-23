package com.borathings.borapagar.subject;

import com.borathings.borapagar.classroom.ClassroomEntity;
import com.borathings.borapagar.core.SoftDeletableModel;
import com.borathings.borapagar.department.DepartmentEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "subject")
@Getter
@Setter
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

    @JsonManagedReference @ManyToOne @NotNull private DepartmentEntity department;

    // Set<SubjectEntity> não funciona aqui por um motivo que desconheço (fica sempre vazio)
    @ManyToMany
    @JoinTable(
            name = "subject_prerequisite",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_id"))
    @JsonManagedReference
    private Set<SubjectEntity> prerequisites;

    @ManyToMany(mappedBy = "prerequisites")
    @JsonBackReference
    private Set<SubjectEntity> prerequisiteOf;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectEntity)) return false;
        SubjectEntity other = (SubjectEntity) o;
        return Objects.equals(getId(), other.getId())
                && Objects.equals(getName(), other.getName())
                && Objects.equals(getCode(), other.getCode())
                && Objects.equals(getSyllabus(), other.getSyllabus());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
