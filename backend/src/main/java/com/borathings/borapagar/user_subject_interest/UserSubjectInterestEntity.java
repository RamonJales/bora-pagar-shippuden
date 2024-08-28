package com.borathings.borapagar.user_subject_interest;

import com.borathings.borapagar.core.SoftDeletableModel;
import com.borathings.borapagar.subject.SubjectEntity;
import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user_semester.UserSemesterEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "user_subject_interest")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserSubjectInterestEntity extends SoftDeletableModel {
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