package com.borathings.borapagar.course;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.borathings.borapagar.course.dto.CourseDTO;
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
        course = CourseEntity.builder().name("TI").coordinator("Fulano").id(1L).build();

        when(courseRepository.findAll()).thenReturn(List.of(course));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.findById(2L))
                .thenThrow(new EntityNotFoundException("Curso não encontrado"));
        when(courseRepository.save(any())).thenAnswer((invocation) -> invocation.getArgument(0));
        doNothing().when(courseRepository).softDeleteById(1L);
    }

    @Test
    public void shouldCreateCourse() {
        CourseDTO courseDto = CourseDTO.fromEntity(course);
        CourseEntity createdCourse = courseService.create(courseDto);
        assertEquals(course.getName(), createdCourse.getName());
        assertEquals(course.getCoordinator(), createdCourse.getCoordinator());
    }

    @Test
    public void shouldGetAllCourses() {
        List<CourseEntity> courses = courseService.findAll();
        assertEquals(1, courses.size());
        assertEquals(course, courses.get(0));
    }

    @Test
    void shouldGetCourseById() {
        CourseEntity course = courseService.findByIdOrError(1L);
        assertEquals(this.course, course);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenRequestNonExistentCourse() {
        assertThrows(EntityNotFoundException.class, () -> courseService.findByIdOrError(2L));
    }

    @Test
    void shouldUpdateCourse() {
        CourseEntity courseCopy = course;
        courseCopy.setId(null);
        CourseDTO courseDto = CourseDTO.fromEntity(courseCopy);
        CourseEntity updatedCourse = courseService.update(1L, courseDto);

        assertEquals(1L, updatedCourse.getId());
        assertEquals(courseCopy.getName(), updatedCourse.getName());
    }

    @Test
    void shouldDeleteCourse() {
        assertDoesNotThrow(() -> courseService.delete(1L));
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdatingNonExistentCourse() {
        CourseEntity courseCopy = course;
        courseCopy.setId(null);
        CourseDTO courseDto = CourseDTO.fromEntity(courseCopy);
        assertThrows(EntityNotFoundException.class, () -> courseService.update(2L, courseDto));
    }
}
