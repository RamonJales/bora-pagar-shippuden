package com.borathings.borapagar.subject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.borathings.borapagar.subject.dto.SubjectDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SubjectController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebMvc
public class SubjectControllerTests {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private SubjectService subjectService;

    private SubjectEntity subject;

    @BeforeEach
    public void setUp() {
        subject =
                new SubjectEntity(
                        "Matemática elementar", "IMD0001", "math and stuff", Integer.valueOf(60));

        when(subjectService.findAll()).thenReturn(List.of(subject));
        when(subjectService.findByIdOrError(1L)).thenReturn(subject);
        when(subjectService.findByIdOrError(2L))
                .thenThrow(new EntityNotFoundException("Disciplina não encontrada"));
        when(subjectService.create(any())).thenReturn(subject);
        when(subjectService.update(eq(1L), any())).thenReturn(subject);
        when(subjectService.update(eq(2L), any()))
                .thenThrow(new EntityNotFoundException("Disciplina não encontrada"));
        doNothing().when(subjectService).delete(1L);
    }

    @Test
    public void shouldListAllSubjects() throws Exception {
        this.mockMvc
                .perform(get("/api/subject"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(subject.getId()))
                .andExpect(jsonPath("$[0].name").value(subject.getName()))
                .andExpect(jsonPath("$[0].code").value(subject.getCode()))
                .andExpect(jsonPath("$[0].syllabus").value(subject.getSyllabus()))
                .andExpect(jsonPath("$[0].hours").value(subject.getHours()));
    }

    @Test
    public void shouldListSubject() throws Exception {
        this.mockMvc
                .perform(get("/api/subject/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(subject.getId()))
                .andExpect(jsonPath("$.name").value(subject.getName()))
                .andExpect(jsonPath("$.code").value(subject.getCode()))
                .andExpect(jsonPath("$.syllabus").value(subject.getSyllabus()))
                .andExpect(jsonPath("$.hours").value(subject.getHours()));
    }

    @Test
    public void shouldReturnNotFoundWhenGetNonExistentSubject() throws Exception {
        this.mockMvc
                .perform(get("/api/subject/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void shouldCreateSubject() throws Exception {
        this.mockMvc
                .perform(
                        post("/api/subject")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new SubjectDTO(
                                                        "Matemática elementar",
                                                        "IMD0001",
                                                        "math and stuff",
                                                        Integer.valueOf(60)))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(subject.getId()))
                .andExpect(jsonPath("$.name").value(subject.getName()))
                .andExpect(jsonPath("$.code").value(subject.getCode()))
                .andExpect(jsonPath("$.syllabus").value(subject.getSyllabus()))
                .andExpect(jsonPath("$.hours").value(subject.getHours()));
    }

    @Test
    public void shouldValidateFieldsOnCreate() throws Exception {
        this.mockMvc
                .perform(post("/api/subject").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fieldErrors.name").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors.code").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors.hours").isNotEmpty());

        this.mockMvc
                .perform(
                        post("/api/subject")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new SubjectDTO(null, null, null, -60))))
                .andExpect(jsonPath("$.fieldErrors.hours").isNotEmpty());
    }

    @Test
    public void shouldUpdateSubject() throws Exception {
        this.mockMvc
                .perform(
                        put("/api/subject/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new SubjectDTO("ME", "IMD0001", "syllabus", 100))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(subject.getId()))
                .andExpect(jsonPath("$.name").value(subject.getName()))
                .andExpect(jsonPath("$.code").value(subject.getCode()))
                .andExpect(jsonPath("$.syllabus").value(subject.getSyllabus()))
                .andExpect(jsonPath("$.hours").value(subject.getHours()));
    }

    @Test
    public void shouldReturnNotFoundWhenUpdatingNonExistentSubject() throws Exception {
        this.mockMvc
                .perform(
                        put("/api/subject/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new SubjectDTO("ME", "IMD0001", "program", 100))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void shouldValidateFieldsOnUpdate() throws Exception {
        this.mockMvc
                .perform(
                        put("/api/subject/1").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fieldErrors.name").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors.code").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors.hours").isNotEmpty());

        this.mockMvc
                .perform(
                        put("/api/subject/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new SubjectDTO(null, null, null, -60))))
                .andExpect(jsonPath("$.fieldErrors.hours").isNotEmpty());
    }

    @Test
    public void shouldDeleteSubject() throws Exception {
        this.mockMvc.perform(delete("/api/subject/1")).andExpect(status().isOk());
    }
}
