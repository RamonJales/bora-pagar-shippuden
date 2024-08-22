package com.borathings.borapagar.course.dto;

import com.borathings.borapagar.course.CourseEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CourseDTO {
    @NotBlank private String name;
    @NotBlank private String coordinator;

    /**
     * @return CourseEntity - Entidade de curso
     */
    public CourseEntity toEntity() {
        CourseEntity courseEntity =
                CourseEntity.builder().name(name).coordinator(coordinator).build();
        return courseEntity;
    }

    public static CourseDTO fromEntity(CourseEntity entity) {
        return CourseDTO.builder()
                .name(entity.getName())
                .coordinator(entity.getCoordinator())
                .build();
    }
}
