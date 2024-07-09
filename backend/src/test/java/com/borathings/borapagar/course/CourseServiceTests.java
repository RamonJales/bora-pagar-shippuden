package com.borathings.borapagar.course;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseServiceTests {
    @MockBean private CourseRepository courseRepository;

    @Autowired private CourseService courseService;

    private CourseEntity course;

    @BeforeEach
    public void setUp() {
        course = new CourseEntity();
        course.setName("TI");
        course.setCoordinator("Fulano");
        course.setId(1L);
        course.setDeleted(false);

        when(courseRepository.findAll()).thenReturn(List.of(course));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.findById(2L)).thenThrow(EntityNotFoundException.class);
        when(courseRepository.save(course)).thenReturn(course);
        doNothing().when(courseRepository).deleteById(1L);
    }

    @Test
    public void shouldCreateCourse() {
        CourseEntity createdCourse = courseService.create(course);
        assert createdCourse.equals(course);
    }

    @Test
    public void shouldGetAllCourses() {
        List<CourseEntity> courses = courseService.findAll();
        assert courses.size() == 1;
        assert courses.get(0).equals(course);
    }

    @Test
    void shouldGetCourseById() {
        CourseEntity course = courseService.findByIdOrError(1L);
        assert course.equals(this.course);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenRequestNonExistentCourse() {
        try {
            courseService.findByIdOrError(2L);
        } catch (EntityNotFoundException e) {
            assert true;
            return;
        }

        assert false;
    }

    @Test
    void shouldUpdateCourse() {
        CourseEntity courseCopy = course;
        courseCopy.setId(null);
        CourseEntity updatedCourse = courseService.update(1L, courseCopy);

        assert updatedCourse.getId().equals(1L);
        assert updatedCourse.getName().equals(courseCopy.getName());
    }

    @Test
    void shouldDeleteCourse() {
        courseService.delete(1L);
        assert true;
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdatingNonExistentCourse() {
        try {
            CourseEntity courseCopy = course;
            courseCopy.setId(null);
            courseService.update(2L, courseCopy);
        } catch (EntityNotFoundException e) {
            assert true;
            return;
        }

        assert false;
    }
}
