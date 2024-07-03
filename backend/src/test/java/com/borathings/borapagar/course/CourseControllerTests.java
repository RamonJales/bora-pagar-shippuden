package com.borathings.borapagar.course;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.borathings.borapagar.course.dto.CourseDTO;
import com.borathings.borapagar.course.enumTypes.CourseLevel;

@WebMvcTest(CourseController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebMvc
public class CourseControllerTests {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private CourseService courseService;

    private CourseEntity course;
    private CourseDTO courseDTO;

    @BeforeEach
    public void setUpService() {
        course = new CourseEntity();
        course.setName("TI");
        course.setCourseLevel(CourseLevel.GRADUATION);
        course.setCoordinator("Fulano");
        course.setId(1L);
        course.setDeleted(false);

        courseDTO = new CourseDTO();
        courseDTO.setName("TI");
        courseDTO.setCourseLevel(CourseLevel.GRADUATION);
        courseDTO.setCoordinator("Fulano");


        when(courseService.getAllCourses()).thenReturn(List.of(course));
        when(courseService.getCourseById(1L)).thenReturn(course);
        when(courseService.createCourse(courseDTO)).thenReturn(course);
        when(courseService.updateCourse(1L, courseDTO)).thenReturn(course);
        doNothing().when(courseService).deleteCourse(1L);;
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
                .andExpect(jsonPath("$[0].coordinator").value(course.getCoordinator()))
                ;
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
                .andExpect(jsonPath("$.coordinator").value(course.getCoordinator()))
                ;
    }

    @Test
    public void shouldCreateCourse() throws Exception {
        this.mockMvc
                .perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"TI\", \"courseLevel\": \"GRADUATION\", \"coordinator\": \"Fulano\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(course.getId()))
                .andExpect(jsonPath("$.name").value(course.getName()))
                .andExpect(jsonPath("$.courseLevel").value("GRADUATION"))
                .andExpect(jsonPath("$.coordinator").value(course.getCoordinator()))
                ;
    }

    @Test
    public void shouldValidateFieldsOnCreate() throws Exception {
        this.mockMvc
                .perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.name").value(hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors.coordinator").value(hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors.courseLevel").value(hasSize(1)))
                ;
    }

    @Test
    public void shouldUpdateCourse() throws Exception {
        this.mockMvc
                .perform(put("/course/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"TI\", \"courseLevel\": \"GRADUATION\", \"coordinator\": \"Fulano\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(course.getId()))
                .andExpect(jsonPath("$.name").value(course.getName()))
                .andExpect(jsonPath("$.courseLevel").value("GRADUATION"))
                .andExpect(jsonPath("$.coordinator").value(course.getCoordinator()))
                ;
    }

    @Test
    public void shouldValidateFieldsOnUpdate() throws Exception {
        this.mockMvc
                .perform(put("/course/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.name").value(hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors.coordinator").value(hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors.courseLevel").value(hasSize(1)))
                ;
    }

    @Test
    public void shouldDeleteCourse() throws Exception {
        this.mockMvc
                .perform(delete("/course/1"))
                .andExpect(status().isOk())
                ;
    }
}
