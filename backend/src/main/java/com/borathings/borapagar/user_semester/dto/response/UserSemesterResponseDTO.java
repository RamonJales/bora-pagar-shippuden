package com.borathings.borapagar.user_semester.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSemesterResponseDTO {
    private Long id;
    private Integer year;
    private Integer period;
}
