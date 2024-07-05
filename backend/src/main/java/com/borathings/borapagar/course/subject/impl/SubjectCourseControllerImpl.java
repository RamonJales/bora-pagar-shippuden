package com.borathings.borapagar.course.subject.impl;

import com.borathings.borapagar.course.subject.SubjectCourseController;
import com.borathings.borapagar.course.subject.SubjectCourseEntity;
import com.borathings.borapagar.course.subject.SubjectCourseService;
import com.borathings.borapagar.course.subject.dto.CreateSubjectCourseRequest;
import com.borathings.borapagar.course.subject.dto.CreateSubjectCourseResponse;
import com.borathings.borapagar.course.subject.dto.GetSubjectCourseResponse;
import com.borathings.borapagar.course.subject.dto.UpdateSubjectCourseRequest;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubjectCourseControllerImpl implements SubjectCourseController {

    @Autowired private SubjectCourseService subjectCourseService;

    @Override
    public ResponseEntity<CreateSubjectCourseResponse> addSubjectToCourseSchedule(
            Long courseId, @Valid CreateSubjectCourseRequest subjectCourseCreateDTO) {
        SubjectCourseEntity subjectCourseEntity =
                subjectCourseService.addSubjectToCourseSchedule(
                        courseId, subjectCourseCreateDTO.toEntity());
        CreateSubjectCourseResponse subjectCourseResponseDTO =
                CreateSubjectCourseResponse.fromEntity(subjectCourseEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(subjectCourseResponseDTO);
    }

    @Override
    public ResponseEntity<List<GetSubjectCourseResponse>> getAllSubjectsFromCourseSchedule(
            Long courseId) {
        List<SubjectCourseEntity> courseSchedule =
                subjectCourseService.getAllSubjectsFromCourseSchedule(courseId);
        List<GetSubjectCourseResponse> courseScheduleDTOList = new ArrayList<>();
        for (SubjectCourseEntity subjectCourseEntity : courseSchedule) {
            GetSubjectCourseResponse dto = GetSubjectCourseResponse.fromEntity(subjectCourseEntity);
            courseScheduleDTOList.add(dto);
        }
        return ResponseEntity.ok().body(courseScheduleDTOList);
    }

    @Override
    public ResponseEntity<GetSubjectCourseResponse> getSubjectInfoFromCourseSchedule(
            Long courseId, Long subjectId) {
        SubjectCourseEntity subjectCourseEntity =
                subjectCourseService.getSubjectInfoFromCourseSchedule(courseId, subjectId);

        GetSubjectCourseResponse responseBody =
                GetSubjectCourseResponse.fromEntity(subjectCourseEntity);

        return ResponseEntity.ok().body(responseBody);
    }

    @Override
    public ResponseEntity<GetSubjectCourseResponse> updateSubjectInfoFromCourseSchedule(
            Long courseId,
            Long subjectId,
            @Valid UpdateSubjectCourseRequest subjectCourseCreateDTO) {
        SubjectCourseEntity subjectCourseEntity =
                subjectCourseService.updateSubjectInfoFromCourseSchedule(
                        courseId, subjectId, subjectCourseCreateDTO.toEntity());
        GetSubjectCourseResponse subjectCourseResponseDTO =
                GetSubjectCourseResponse.fromEntity(subjectCourseEntity);
        return ResponseEntity.ok().body(subjectCourseResponseDTO);
    }

    @Override
    public ResponseEntity<String> removeSubjectFromCourseSchedule(Long courseId, Long subjectId) {
        subjectCourseService.deleteSubjectFromCourseSchedule(courseId, subjectId);
        return ResponseEntity.ok().body("Subject removed from course schedule");
    }
}
