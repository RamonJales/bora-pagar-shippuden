package com.borathings.borapagar.user_interest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserInterestDTO {
    private Long userSemesterId;
    private Boolean completed;
}
