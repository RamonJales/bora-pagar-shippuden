package com.borathings.borapagar.user_interest.dto;

import com.borathings.borapagar.user_interest.UserInterestEntity;
import com.borathings.borapagar.user_semester.UserSemesterEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserInterestDTO {
    private Long userSemesterId;
    private Boolean completed;

    public UserInterestEntity toEntity() {
        return UserInterestEntity.builder()
                .userSemester(UserSemesterEntity.builder().id(userSemesterId).build())
                .completed(completed)
                .build();
    }
}
