package com.borathings.borapagar.user_semester.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UpdateUserSemesterDTO {
    @NotNull
    @Min(0)
    private Integer year;

    @NotNull
    @Min(0)
    private Integer period;
}
