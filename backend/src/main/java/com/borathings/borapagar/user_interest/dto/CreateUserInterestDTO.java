package com.borathings.borapagar.user_interest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserInterestDTO {
    @NotNull
    private Long userSemesterId;
};
