package com.borathings.borapagar.subject_course;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.borathings.borapagar.course.CourseEntity;
import com.borathings.borapagar.course.CourseService;
import com.borathings.borapagar.subject.SubjectEntity;
import com.borathings.borapagar.subject_course.dto.CreateSubjectCourseRequest;
import com.borathings.borapagar.subject_course.dto.UpdateSubjectCourseRequest;
import com.borathings.borapagar.subject_course.enumTypes.SubjectCourseType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubjectCourseControllerTests {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private SubjectCourseService subjectCourseService;
    @MockBean private CourseService courseService;

    private final String SUBJECT_NOT_FOUND = "Matéria não encontrada";
    private final String COURSE_NOT_FOUND = "Curso não encontrado";
    private final String SUBJECT_COURSE_NOT_FOUND = "Disciplina não encontrada na grade do curso";

    private final CourseEntity course = new CourseEntity("TI", "Fulano", new ArrayList<>());
    private final SubjectEntity subject =
            new SubjectEntity("Nome", "IMD0001", "Descrição", 60, new ArrayList<>());
    private final SubjectCourseEntity subjectCourse =
            new SubjectCourseEntity(
                    new SubjectCourseKey(1L, 1L), subject, course, 1, SubjectCourseType.MANDATORY);
    private final SubjectCourseEntity updatedSubjectCourse =
            new SubjectCourseEntity(
                    new SubjectCourseKey(1L, 1L), subject, course, 1, SubjectCourseType.OPTIONAL);

    @BeforeEach
    public void setUp() {
        when(courseService.findByIdOrError(1L)).thenReturn(course);
        when(courseService.findByIdOrError(2L))
                .thenThrow(new EntityNotFoundException(COURSE_NOT_FOUND));
        when(subjectCourseService.addSubjectToCourseSchedule(
                        eq(1L), argThat(arg -> arg.getKeyId().getSubjectId().equals(1L))))
                .thenReturn(subjectCourse);
        when(subjectCourseService.addSubjectToCourseSchedule(
                        eq(1L), argThat(arg -> arg.getKeyId().getSubjectId().equals(2L))))
                .thenThrow(new EntityNotFoundException(SUBJECT_NOT_FOUND));
        when(subjectCourseService.addSubjectToCourseSchedule(
                        eq(1L), argThat(arg -> arg.getKeyId().getSubjectId().equals(3L))))
                .thenThrow(new DuplicateKeyException("Matéria já adicionada ao curso"));
        when(subjectCourseService.getAllSubjectsFromCourseSchedule(1L))
                .thenReturn(List.of(subjectCourse));
        when(subjectCourseService.getSubjectInfoFromCourseScheduleOrError(1L, 1L))
                .thenReturn(subjectCourse);
        when(subjectCourseService.getSubjectInfoFromCourseScheduleOrError(1L, 2L))
                .thenThrow(new EntityNotFoundException(SUBJECT_COURSE_NOT_FOUND));
        when(subjectCourseService.updateSubjectInfoFromCourseSchedule(eq(1L), eq(1L), any()))
                .thenReturn(updatedSubjectCourse);
        when(subjectCourseService.updateSubjectInfoFromCourseSchedule(eq(1L), eq(2L), any()))
                .thenThrow(new EntityNotFoundException(SUBJECT_COURSE_NOT_FOUND));
    }

    @Test
    public void shouldAddSubjectToSchedule() throws Exception {
        CreateSubjectCourseRequest subjectCourse =
                new CreateSubjectCourseRequest(1L, 1, SubjectCourseType.MANDATORY);
        this.mockMvc
                .perform(
                        post("/api/course/1/subject")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(subjectCourse)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.keyId.subjectId").value(subjectCourse.getSubjectId()))
                .andExpect(jsonPath("$.keyId.courseId").value(1L))
                .andExpect(
                        jsonPath("$.expectedSemester").value(subjectCourse.getExpectedSemester()))
                .andExpect(
                        jsonPath("$.subjectCourseType")
                                .value(subjectCourse.getSubjectCourseType().toString()));
    }

    @Test
    public void addToScheduleShouldVerifyIfEntitiesExist() throws Exception {
        CreateSubjectCourseRequest subjectCourse =
                new CreateSubjectCourseRequest(2L, 1, SubjectCourseType.MANDATORY);
        this.mockMvc
                .perform(
                        post("/api/course/2/subject")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(subjectCourse)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(COURSE_NOT_FOUND));

        this.mockMvc
                .perform(
                        post("/api/course/1/subject")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(subjectCourse)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(SUBJECT_NOT_FOUND));
    }

    @Test
    public void addToScheduleShouldVerifyIfThereExistsDuplicates() throws Exception {
        this.mockMvc
                .perform(
                        post("/api/course/1/subject")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new CreateSubjectCourseRequest(
                                                        3L, 1, SubjectCourseType.MANDATORY))))
                .andExpect(status().isConflict());
    }

    @Test
    public void addToScheduleShouldValidateFields() throws Exception {
        this.mockMvc
                .perform(
                        post("/api/course/1/subject")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new CreateSubjectCourseRequest(
                                                        2L, 0, SubjectCourseType.MANDATORY))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.expectedSemester").isNotEmpty());

        this.mockMvc
                .perform(
                        post("/api/course/1/subject")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new CreateSubjectCourseRequest(null, 1, null))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.subjectCourseType").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors.subjectId").isNotEmpty());
    }

    @Test
    public void shouldGetAllSubjectsFromSchedule() throws Exception {
        this.mockMvc
                .perform(get("/api/course/1/subject"))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$[0].keyId.subjectId")
                                .value(subjectCourse.getKeyId().getSubjectId()))
                .andExpect(
                        jsonPath("$[0].keyId.courseId")
                                .value(subjectCourse.getKeyId().getCourseId()))
                .andExpect(
                        jsonPath("$[0].expectedSemester")
                                .value(subjectCourse.getExpectedSemester()))
                .andExpect(
                        jsonPath("$[0].subjectCourseType")
                                .value(subjectCourse.getSubjectCourseType().toString()));
    }

    @Test
    public void getAllSubjectsShouldVerifyIfEntitiesExist() throws Exception {
        this.mockMvc
                .perform(get("/api/course/2/subject"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(COURSE_NOT_FOUND));
    }

    @Test
    public void shouldGetSubjectInfoFromSchedule() throws Exception {
        this.mockMvc
                .perform(get("/api/course/1/subject/1"))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.keyId.subjectId")
                                .value(subjectCourse.getKeyId().getSubjectId()))
                .andExpect(
                        jsonPath("$.keyId.courseId").value(subjectCourse.getKeyId().getCourseId()))
                .andExpect(
                        jsonPath("$.expectedSemester").value(subjectCourse.getExpectedSemester()))
                .andExpect(
                        jsonPath("$.subjectCourseType")
                                .value(subjectCourse.getSubjectCourseType().toString()));
        ;
    }

    @Test
    public void getSubjectInfoShouldVerifyIfEntitiesExist() throws Exception {
        this.mockMvc
                .perform(get("/api/course/2/subject/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(COURSE_NOT_FOUND));

        this.mockMvc
                .perform(get("/api/course/1/subject/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(SUBJECT_COURSE_NOT_FOUND));
    }

    @Test
    public void shouldUpdateSubjectInfoFromSchedule() throws Exception {
        this.mockMvc
                .perform(
                        put("/api/course/1/subject/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new UpdateSubjectCourseRequest(
                                                        1, SubjectCourseType.OPTIONAL))))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.keyId.subjectId")
                                .value(updatedSubjectCourse.getKeyId().getSubjectId()))
                .andExpect(
                        jsonPath("$.keyId.courseId")
                                .value(updatedSubjectCourse.getKeyId().getCourseId()))
                .andExpect(
                        jsonPath("$.expectedSemester")
                                .value(updatedSubjectCourse.getExpectedSemester()))
                .andExpect(
                        jsonPath("$.subjectCourseType")
                                .value(updatedSubjectCourse.getSubjectCourseType().toString()));
    }

    @Test
    public void updateSubjectInfoShouldVerifyIfEntitiesExist() throws Exception {
        this.mockMvc
                .perform(
                        put("/api/course/2/subject/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new UpdateSubjectCourseRequest(
                                                        1, SubjectCourseType.OPTIONAL))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(COURSE_NOT_FOUND));

        this.mockMvc
                .perform(
                        put("/api/course/1/subject/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new UpdateSubjectCourseRequest(
                                                        1, SubjectCourseType.OPTIONAL))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(SUBJECT_COURSE_NOT_FOUND));
    }

    @Test
    public void updateSubjectInfoShouldValidateFields() throws Exception {
        this.mockMvc
                .perform(
                        put("/api/course/1/subject/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new UpdateSubjectCourseRequest(0, null))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.expectedSemester").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors.subjectCourseType").isNotEmpty());
    }

    @Test
    public void shouldRemoveSubjectFromSchedule() throws Exception {
        this.mockMvc.perform(delete("/api/course/1/subject/1")).andExpect(status().isOk());
    }

    @Test
    public void shouldRemoveCheckIfCourseExist() throws Exception {
        this.mockMvc
                .perform(delete("/api/course/2/subject/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(COURSE_NOT_FOUND));
    }
}
