package com.borathings.borapagar.subject.dto;

import com.borathings.borapagar.subject.SubjectEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectDTO {
    @NotBlank private String name;

    @NotBlank private String code;

    @NotBlank private String syllabus;

    @Min(0)
    @NotNull
    private Integer hours;

    public SubjectEntity toEntity() {
        SubjectEntity entity = new SubjectEntity();
        entity.setName(name);
        entity.setCode(code);
        entity.setSyllabus(syllabus);
        entity.setHours(hours);
        return entity;
    }
}
