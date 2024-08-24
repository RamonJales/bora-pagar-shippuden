package com.borathings.borapagar.user_semester.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserSemesterDTO {
    @NotNull
    @Min(0)
    Integer year;

    @NotNull
    @Min(0)
    Integer period;
}
