package com.borathings.borapagar.user;

import com.borathings.borapagar.classroom.ClassroomEntity;
import com.borathings.borapagar.core.AbstractModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.NaturalId;

/** UserEntity */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserEntity extends AbstractModel {
    @Column @NotNull private String email;
    @Column @NotNull private String name;
    @Column @NotNull @NaturalId private String googleId;
    @Column private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "enrollment",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "classroom_id"))
    private Set<ClassroomEntity> enrolledClassrooms;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity other = (UserEntity) o;
        return Objects.equals(getGoogleId(), other.getGoogleId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getGoogleId());
    }
}
