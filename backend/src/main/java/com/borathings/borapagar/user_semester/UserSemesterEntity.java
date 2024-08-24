package com.borathings.borapagar.user_semester;

import com.borathings.borapagar.core.SoftDeletableModel;
import com.borathings.borapagar.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "user_semester")
@Table(
        indexes = {
            @Index(
                    name = "user_id_year_period_unique",
                    columnList = "user_id, semester_year, period",
                    unique = true)
        })
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserSemesterEntity extends SoftDeletableModel {

    @JsonIgnore @ManyToOne @NotNull private UserEntity user;

    @Column(name = "semester_year")
    @NotNull
    private Integer year;

    @Column @NotNull private Integer period;
}
