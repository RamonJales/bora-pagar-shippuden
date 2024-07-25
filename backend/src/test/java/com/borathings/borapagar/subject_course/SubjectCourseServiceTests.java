package com.borathings.borapagar.subject_course;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.borathings.borapagar.course.CourseEntity;
import com.borathings.borapagar.course.CourseService;
import com.borathings.borapagar.subject.SubjectEntity;
import com.borathings.borapagar.subject.SubjectService;
import com.borathings.borapagar.subject_course.enumTypes.SubjectCourseType;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubjectCourseServiceTests {

    @MockBean private SubjectCourseRepository subjectCourseRepository;
    @MockBean private CourseService courseService;
    @MockBean private SubjectService subjectService;

    @Autowired private SubjectCourseService subjectCourseService;

    private final CourseEntity course = new CourseEntity("TI", "Fulano", new ArrayList<>());
    private final SubjectEntity subject =
            new SubjectEntity("Nome", "IMD0001", "Descrição", 60, new ArrayList<>());

    private final SubjectCourseKey subjectCourseKey =
            SubjectCourseKey.builder().subjectId(2L).courseId(1L).build();
    private final SubjectCourseEntity subjectCourse =
            SubjectCourseEntity.builder()
                    .subject(subject)
                    .course(course)
                    .expectedSemester(1)
                    .subjectCourseType(SubjectCourseType.MANDATORY)
                    .keyId(subjectCourseKey)
                    .build();

    @BeforeEach
    public void setUp() {
        when(courseService.findByIdOrError(1L)).thenReturn(course);
        when(subjectService.findByIdOrError(1L)).thenReturn(subject);
        when(subjectCourseRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(subjectCourseRepository.findByCourseIdAndSubjectId(1L, 1L))
                .thenReturn(Optional.empty());
        when(subjectCourseRepository.findByCourseIdAndSubjectId(1L, 2L))
                .thenReturn(Optional.of(subjectCourse));
        when(subjectService.findByIdOrError(3L))
                .thenThrow(new EntityNotFoundException("Disciplina não encontrada"));

        when(subjectCourseRepository.findByCourseId(1L)).thenReturn(List.of(subjectCourse));
        doNothing().when(subjectCourseRepository).deleteByCourseIdAndSubjectId(1L, 1L);
    }

    @Test
    public void shouldAddSubjectToCourseSchedule() throws Exception {
        SubjectCourseKey subjectCourseKey = SubjectCourseKey.builder().subjectId(1L).build();
        SubjectCourseEntity subjectCourse =
                SubjectCourseEntity.builder()
                        .keyId(subjectCourseKey)
                        .subject(subject)
                        .expectedSemester(1)
                        .subjectCourseType(SubjectCourseType.MANDATORY)
                        .build();
        SubjectCourseEntity createdSubjectCourse =
                subjectCourseService.addSubjectToCourseSchedule(1L, subjectCourse);

        assertEquals(createdSubjectCourse.getKeyId().getCourseId(), 1L);
        assertEquals(createdSubjectCourse.getKeyId().getSubjectId(), 1L);
        assertEquals(createdSubjectCourse.getExpectedSemester(), 1);
        assertEquals(createdSubjectCourse.getSubjectCourseType(), SubjectCourseType.MANDATORY);
    }

    @Test
    public void addSubjectShouldThrowWhenDuplicatesExist() {
        SubjectCourseKey subjectCourseKey = SubjectCourseKey.builder().subjectId(2L).build();
        SubjectCourseEntity subjectCourse =
                SubjectCourseEntity.builder()
                        .keyId(subjectCourseKey)
                        .subject(subject)
                        .expectedSemester(1)
                        .subjectCourseType(SubjectCourseType.MANDATORY)
                        .build();
        assertThrows(
                DuplicateKeyException.class,
                () -> {
                    subjectCourseService.addSubjectToCourseSchedule(1L, subjectCourse);
                });
    }

    @Test
    public void addSubjectShouldThrowWhenSubjectDoesntExist() {
        SubjectCourseKey subjectCourseKey = SubjectCourseKey.builder().subjectId(3L).build();
        SubjectCourseEntity subjectCourse =
                SubjectCourseEntity.builder()
                        .keyId(subjectCourseKey)
                        .subject(subject)
                        .expectedSemester(1)
                        .subjectCourseType(SubjectCourseType.MANDATORY)
                        .build();
        assertThrows(
                EntityNotFoundException.class,
                () -> {
                    subjectCourseService.addSubjectToCourseSchedule(1L, subjectCourse);
                });
    }

    @Test
    public void shouldGetSubjectInfoFromCourseSchedule() {
        assertEquals(
                subjectCourseService.getSubjectInfoFromCourseScheduleOrError(1L, 2L),
                subjectCourse);
    }

    @Test
    public void getSubjectInfoShouldThrowIfSubjectInfoNotFound() {
        assertThrows(
                EntityNotFoundException.class,
                () -> {
                    subjectCourseService.getSubjectInfoFromCourseScheduleOrError(1L, 1L);
                });
    }

    @Test
    public void shouldGetAllSubjectsFromCourseSchedule() {
        assertEquals(
                subjectCourseService.getAllSubjectsFromCourseSchedule(1L), List.of(subjectCourse));
    }

    @Test
    public void shouldUpdateSubjectInfoFromCourseSchedule() {
        SubjectCourseKey subjectCourseKey = SubjectCourseKey.builder().subjectId(2L).build();
        SubjectCourseEntity newSubjectCourse =
                SubjectCourseEntity.builder()
                        .keyId(subjectCourseKey)
                        .expectedSemester(2)
                        .subjectCourseType(SubjectCourseType.OPTIONAL)
                        .build();

        SubjectCourseEntity updatedSubjectCourse =
                subjectCourseService.updateSubjectInfoFromCourseSchedule(1L, 2L, newSubjectCourse);
        assertEquals(updatedSubjectCourse.getKeyId().getCourseId(), 1L);
        assertEquals(updatedSubjectCourse.getKeyId().getSubjectId(), 2L);
        assertEquals(updatedSubjectCourse.getSubject(), subject);
        assertEquals(updatedSubjectCourse.getCourse(), course);
        assertEquals(updatedSubjectCourse.getExpectedSemester(), 2);
        assertEquals(updatedSubjectCourse.getSubjectCourseType(), SubjectCourseType.OPTIONAL);
    }

    @Test
    public void updateSubjectInfoShouldThrowIfSubjectInfoNotFound() {
        SubjectCourseKey subjectCourseKey = SubjectCourseKey.builder().subjectId(1L).build();
        SubjectCourseEntity newSubjectCourse =
                SubjectCourseEntity.builder()
                        .keyId(subjectCourseKey)
                        .expectedSemester(2)
                        .subjectCourseType(SubjectCourseType.OPTIONAL)
                        .build();
        assertThrows(
                EntityNotFoundException.class,
                () ->
                        subjectCourseService.updateSubjectInfoFromCourseSchedule(
                                1L, 1L, newSubjectCourse));
    }

    @Test
    public void shouldRemoveSubjectFromCourseSchedule() {
        assertDoesNotThrow(() -> subjectCourseService.deleteSubjectFromCourseSchedule(1L, 1L));
    }
}
