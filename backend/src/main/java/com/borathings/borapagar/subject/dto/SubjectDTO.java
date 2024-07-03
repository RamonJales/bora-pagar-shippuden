package com.borathings.borapagar.subject.dto;

import com.borathings.borapagar.subject.SubjectEntity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubjectDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String code;

    @NotBlank
    private String program;

    @Min(0)
    @NotNull
    private Integer hours;

    public SubjectEntity toEntity() {
        SubjectEntity entity = new SubjectEntity();
        entity.setName(name);
        entity.setCode(code);
        entity.setProgram(program);
        entity.setHours(hours);
        return entity;
    }
}
