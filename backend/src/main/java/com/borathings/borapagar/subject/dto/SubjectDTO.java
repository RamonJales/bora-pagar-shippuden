package com.borathings.borapagar.subject.dto;

import com.borathings.borapagar.department.DepartmentEntity;
import com.borathings.borapagar.subject.SubjectEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SubjectDTO {
    @NotBlank private String name;

    @NotBlank private String code;

    @NotBlank private String syllabus;

    @Min(0)
    @NotNull
    private Integer hours;

    @NotNull private Long departmentId;

    public SubjectEntity toEntity() {
        DepartmentEntity department = DepartmentEntity.builder().id(departmentId).build();
        SubjectEntity entity =
                SubjectEntity.builder()
                        .name(name)
                        .code(code)
                        .syllabus(syllabus)
                        .hours(hours)
                        .department(department)
                        .build();
        return entity;
    }

    public static SubjectDTO fromEntity(SubjectEntity entity) {
        return SubjectDTO.builder()
                .name(entity.getName())
                .code(entity.getCode())
                .syllabus(entity.getSyllabus())
                .hours(entity.getHours())
                .departmentId(entity.getDepartment().getId())
                .build();
    }
}
