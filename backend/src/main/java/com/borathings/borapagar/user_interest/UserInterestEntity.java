package com.borathings.borapagar.user_interest;

import com.borathings.borapagar.core.SoftDeletableModel;
import com.borathings.borapagar.subject.SubjectEntity;
import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user_semester.UserSemesterEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "user_interest")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UserInterestEntity extends SoftDeletableModel {
    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private SubjectEntity subject;

    @ManyToOne
    private UserSemesterEntity userSemester;
    
    @Column
    @Builder.Default
    private Boolean completed = false;
}