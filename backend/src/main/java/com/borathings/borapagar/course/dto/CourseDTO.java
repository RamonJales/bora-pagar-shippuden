package com.borathings.borapagar.course.dto;

import com.borathings.borapagar.course.CourseEntity;
import com.borathings.borapagar.course.enumTypes.CourseLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseDTO {
    @NotBlank private String name;

    @NotNull
    // TODO: Implementar validação de enums
    private CourseLevel courseLevel;

    @NotBlank private String coordinator;

    /**
     * @return CourseEntity - Entidade de curso
     */
    public CourseEntity toEntity() {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName(name);
        courseEntity.setCourseLevel(courseLevel);
        courseEntity.setCoordinator(coordinator);
        return courseEntity;
    }
}
