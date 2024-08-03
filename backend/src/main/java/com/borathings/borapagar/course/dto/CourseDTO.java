package com.borathings.borapagar.course.dto;

import com.borathings.borapagar.course.CourseEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
}
