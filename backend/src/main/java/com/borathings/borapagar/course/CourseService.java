package com.borathings.borapagar.course;

import com.borathings.borapagar.course.dto.CourseDTO;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired private CourseRepository courseRepository;

    public CourseEntity createCourse(CourseDTO courseEntityDto) {
        return courseRepository.save(courseEntityDto.toEntity());
    }

    public List<CourseEntity> findAllCourses() {
        return courseRepository.findAll();
    }

    public CourseEntity findCourseById(Long id) {
        CourseEntity courseEntity = courseRepository.findById(id).orElse(null);
        if (courseEntity == null) {
            throw new EntityNotFoundException("Course of id " + id + " not found");
        }
        return courseEntity;
    }

    public CourseEntity updateCourse(Long id, CourseDTO courseEntityDto) {
        CourseEntity courseEntity = findCourseById(id);
        courseEntity = courseEntityDto.toEntity();
        courseEntity.setId(id);
        return courseRepository.save(courseEntity);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
