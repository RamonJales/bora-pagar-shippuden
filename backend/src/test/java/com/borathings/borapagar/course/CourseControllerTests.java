package com.borathings.borapagar.course;

import static org.hamcrest.Matchers.hasSize;
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

import com.borathings.borapagar.course.enumTypes.CourseLevel;
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

@WebMvcTest(CourseController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebMvc
public class CourseControllerTests {
    @Autowired private MockMvc mockMvc;

    @MockBean private CourseService courseService;

    private CourseEntity course;

    @BeforeEach
    public void setUpService() {
        course = new CourseEntity();
        course.setName("TI");
        course.setCourseLevel(CourseLevel.GRADUATION);
        course.setCoordinator("Fulano");
        course.setId(1L);
        course.setDeleted(false);

        when(courseService.getAllCourses()).thenReturn(List.of(course));
        when(courseService.getCourseById(eq(1L))).thenReturn(course);
        when(courseService.getCourseById(eq(2L))).thenThrow(EntityNotFoundException.class);

        when(courseService.createCourse(any())).thenReturn(course);
        when(courseService.updateCourse(eq(1L), any())).thenReturn(course);
        when(courseService.updateCourse(eq(2L), any())).thenThrow(EntityNotFoundException.class);
        doNothing().when(courseService).deleteCourse(eq(1L));
        ;
    }

    @Test
    public void shouldReturnCourses() throws Exception {
        this.mockMvc
                .perform(get("/course"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(course.getId()))
                .andExpect(jsonPath("$[0].name").value(course.getName()))
                .andExpect(jsonPath("$[0].courseLevel").value("GRADUATION"))
                .andExpect(jsonPath("$[0].coordinator").value(course.getCoordinator()));
    }

    @Test
    public void shouldReturnCourseById() throws Exception {
        this.mockMvc
                .perform(get("/course/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(course.getId()))
                .andExpect(jsonPath("$.name").value(course.getName()))
                .andExpect(jsonPath("$.courseLevel").value("GRADUATION"))
                .andExpect(jsonPath("$.coordinator").value(course.getCoordinator()));
    }

    @Test
    public void shouldCreateCourse() throws Exception {
        this.mockMvc
                .perform(
                        post("/course")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"name\": \"TI\", \"courseLevel\": \"GRADUATION\","
                                                + " \"coordinator\": \"Fulano\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(course.getId()))
                .andExpect(jsonPath("$.name").value(course.getName()))
                .andExpect(jsonPath("$.courseLevel").value("GRADUATION"))
                .andExpect(jsonPath("$.coordinator").value(course.getCoordinator()));
    }

    @Test
    public void shouldValidateFieldsOnCreate() throws Exception {
        this.mockMvc
                .perform(post("/course").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.name").value(hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors.coordinator").value(hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors.courseLevel").value(hasSize(1)));
    }

    @Test
    public void shouldUpdateCourse() throws Exception {
        this.mockMvc
                .perform(
                        put("/course/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"name\": \"TI\", \"courseLevel\": \"GRADUATION\","
                                                + " \"coordinator\": \"Fulano\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(course.getId()))
                .andExpect(jsonPath("$.name").value(course.getName()))
                .andExpect(jsonPath("$.courseLevel").value("GRADUATION"))
                .andExpect(jsonPath("$.coordinator").value(course.getCoordinator()));
    }

    @Test
    public void shouldValidateFieldsOnUpdate() throws Exception {
        this.mockMvc
                .perform(put("/course/1").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.name").value(hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors.coordinator").value(hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors.courseLevel").value(hasSize(1)));
    }

    @Test
    public void shouldDeleteCourse() throws Exception {
        this.mockMvc.perform(delete("/course/1")).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhenGetNonExistentCourse() throws Exception {
        this.mockMvc
                .perform(get("/course/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void shouldReturnNotFoundWhenUpdatingNonExistentCourse() throws Exception {
        this.mockMvc
                .perform(
                        put("/course/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"name\": \"TI\", \"courseLevel\": \"GRADUATION\","
                                                + " \"coordinator\": \"Fulano\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }
}
