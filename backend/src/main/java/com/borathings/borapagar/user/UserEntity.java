package com.borathings.borapagar.user;

import com.borathings.borapagar.core.AbstractModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/** UserEntity */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class UserEntity extends AbstractModel {
    @Column @NotNull private String email;
    @Column @NotNull private String name;
    @Column @NotNull private String googleId;
    @Column private String imageUrl;
}
