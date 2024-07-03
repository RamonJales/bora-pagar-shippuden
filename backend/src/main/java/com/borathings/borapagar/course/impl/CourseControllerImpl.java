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
                .body(courseService.createCourse(courseEntityDto.toEntity()));
    }

    @Override
    public ResponseEntity<List<CourseEntity>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @Override
    public ResponseEntity<CourseEntity> getCourseById(Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @Override
    public ResponseEntity<CourseEntity> updateCourse(
            CourseDTO courseEntityDto, Long id) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseEntityDto.toEntity()));
    }

    @Override
    public ResponseEntity<String> deleteCourse(Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully");
    }
}
