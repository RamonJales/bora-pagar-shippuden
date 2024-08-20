package com.borathings.borapagar.department;

import com.borathings.borapagar.core.AbstractModel;
import com.borathings.borapagar.subject.SubjectEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class DepartmentEntity extends AbstractModel {
    @Column @NotNull private String name;

    @Column @NotNull private String link;

    @Column @NotNull private int code;

    @OneToMany(mappedBy = "department")
    private Set<SubjectEntity> subjects;
}
