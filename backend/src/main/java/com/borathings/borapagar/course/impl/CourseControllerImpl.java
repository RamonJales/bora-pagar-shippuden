package com.borathings.borapagar.course.impl;

import com.borathings.borapagar.course.CourseController;
import com.borathings.borapagar.course.CourseEntity;
import com.borathings.borapagar.course.CourseService;
import com.borathings.borapagar.course.dto.CourseDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseControllerImpl implements CourseController {
    @Autowired private CourseService courseService;

    @Override
    public ResponseEntity<CourseEntity> createCourse(
            @Valid @RequestBody CourseDTO courseEntityDto) {
        return ResponseEntity.ok(courseService.createCourse(courseEntityDto));
    }

    @Override
    public ResponseEntity<List<CourseEntity>> findAllCourses() {
        return ResponseEntity.ok(courseService.findAllCourses());
    }

    @Override
    public ResponseEntity<CourseEntity> findCourseById(@RequestParam Long id) {
        return ResponseEntity.ok(courseService.findCourseById(id));
    }

    @Override
    public ResponseEntity<CourseEntity> updateCourse(
            @RequestBody @Valid CourseDTO courseEntityDto, @PathVariable Long id) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseEntityDto));
    }

    @Override
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully");
    }
}
