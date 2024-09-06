package com.borathings.borapagar.course.impl;

import com.borathings.borapagar.course.CourseController;
import com.borathings.borapagar.course.CourseEntity;
import com.borathings.borapagar.course.CourseService;
import com.borathings.borapagar.course.dto.CourseDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseControllerImpl implements CourseController {
    @Autowired private CourseService courseService;

    @Override
    public ResponseEntity<CourseEntity> createCourse(
            @Valid @RequestBody CourseDTO courseEntityDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.create(courseEntityDto));
    }

    @Override
    public ResponseEntity<List<CourseEntity>> getAllCourses() {
        return ResponseEntity.ok(courseService.findAll());
    }

    @Override
    public ResponseEntity<CourseEntity> getCourseById(Long id) {
        return ResponseEntity.ok(courseService.findByIdOrError(id));
    }

    @Override
    public ResponseEntity<CourseEntity> updateCourse(CourseDTO courseEntityDto, Long id) {
        return ResponseEntity.ok(courseService.update(id, courseEntityDto));
    }

    @Override
    public ResponseEntity<String> deleteCourse(Long id) {
        courseService.delete(id);
        return ResponseEntity.ok("Curso deletado com sucesso");
    }
}
