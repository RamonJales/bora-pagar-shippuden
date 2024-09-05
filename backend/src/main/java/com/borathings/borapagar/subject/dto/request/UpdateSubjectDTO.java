package com.borathings.borapagar.subject.dto.request;

import com.borathings.borapagar.department.DepartmentEntity;
import com.borathings.borapagar.subject.SubjectEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateSubjectDTO {
    @NotBlank private String name;

    @NotBlank private String code;

    @NotBlank private String syllabus;

    @Min(0)
    @NotNull
    private Integer hours;

    @NotNull private Long departmentId;

    public SubjectEntity toEntity() {
        DepartmentEntity department = DepartmentEntity.builder().id(departmentId).build();
        return SubjectEntity.builder()
                .name(name)
                .code(code)
                .syllabus(syllabus)
                .hours(hours)
                .department(department)
                .build();
    }
}
