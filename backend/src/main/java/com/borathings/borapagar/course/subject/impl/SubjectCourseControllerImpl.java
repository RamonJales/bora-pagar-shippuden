package com.borathings.borapagar.course.subject.impl;

import com.borathings.borapagar.course.subject.SubjectCourseController;
import com.borathings.borapagar.course.subject.SubjectCourseEntity;
import com.borathings.borapagar.course.subject.SubjectCourseService;
import com.borathings.borapagar.course.subject.dto.CreateSubjectCourseRequest;
import com.borathings.borapagar.course.subject.dto.UpdateSubjectCourseRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubjectCourseControllerImpl implements SubjectCourseController {

    @Autowired private SubjectCourseService subjectCourseService;

    @Override
    public ResponseEntity<SubjectCourseEntity> addSubjectToCourseSchedule(
            Long courseId, @Valid CreateSubjectCourseRequest subjectCourseCreateDTO) {
        SubjectCourseEntity subjectCourseEntity =
                subjectCourseService.addSubjectToCourseSchedule(
                        courseId, subjectCourseCreateDTO.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED).body(subjectCourseEntity);
    }

    @Override
    public ResponseEntity<List<SubjectCourseEntity>> getAllSubjectsFromCourseSchedule(
            Long courseId) {
        List<SubjectCourseEntity> courseSchedule =
                subjectCourseService.getAllSubjectsFromCourseSchedule(courseId);
        return ResponseEntity.ok().body(courseSchedule);
    }

    @Override
    public ResponseEntity<SubjectCourseEntity> getSubjectInfoFromCourseSchedule(
            Long courseId, Long subjectId) {
        SubjectCourseEntity subjectCourseEntity =
                subjectCourseService.getSubjectInfoFromCourseScheduleOrError(courseId, subjectId);

        return ResponseEntity.ok().body(subjectCourseEntity);
    }

    @Override
    public ResponseEntity<SubjectCourseEntity> updateSubjectInfoFromCourseSchedule(
            Long courseId,
            Long subjectId,
            @Valid UpdateSubjectCourseRequest subjectCourseCreateDTO) {
        SubjectCourseEntity subjectCourseEntity =
                subjectCourseService.updateSubjectInfoFromCourseSchedule(
                        courseId, subjectId, subjectCourseCreateDTO.toEntity());

        return ResponseEntity.ok().body(subjectCourseEntity);
    }

    @Override
    public ResponseEntity<String> removeSubjectFromCourseSchedule(Long courseId, Long subjectId) {
        subjectCourseService.deleteSubjectFromCourseSchedule(courseId, subjectId);
        return ResponseEntity.ok().body("Disciplina removida da grade do curso com sucesso");
    }
}
