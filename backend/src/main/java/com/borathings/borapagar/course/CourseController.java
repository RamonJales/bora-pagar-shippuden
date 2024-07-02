package com.borathings.borapagar.course;

import com.borathings.borapagar.course.dto.CourseDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping("/course")
public interface CourseController {

    @PostMapping()
    public ResponseEntity<CourseEntity> createCourse(@Valid @RequestBody CourseDTO courseEntityDto);

    @GetMapping()
    public ResponseEntity<List<CourseEntity>> findAllCourses();

    @GetMapping("/{id}")
    public ResponseEntity<CourseEntity> findCourseById(@RequestParam Long id);

    @PatchMapping("/{id}")
    public ResponseEntity<CourseEntity> updateCourse(
            @RequestBody @Valid CourseDTO courseEntityDto, @PathVariable Long id);

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id);
}
