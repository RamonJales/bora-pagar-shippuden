package com.borathings.borapagar.user;

import com.borathings.borapagar.core.AbstractModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** UserEntity */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends AbstractModel {
    @Column @NotNull private String email;
    @Column @NotNull private String name;
    @Column @NotNull private String googleId;
    @Column private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;
        return getEmail().equals(that.getEmail())
                && getName().equals(that.getName())
                && getGoogleId().equals(that.getGoogleId())
                && Objects.equals(getImageUrl(), that.getImageUrl());
    }

    @Override
    public int hashCode() {
        int result = getEmail().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getGoogleId().hashCode();
        result = 31 * result + Objects.hashCode(getImageUrl());
        return result;
    }
}
