package com.borathings.borapagar.user_planning;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.borathings.borapagar.user_planning.dto.CreateUserPlanningDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserPlanningController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebMvc
public class UserPlanningControllerTests {
    @MockBean UserPlanningService userPlanningService;
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    public void shouldCreatePlanning() throws Exception {
        when(userPlanningService.create(eq("123"), eq(1L), any()))
                .thenReturn(UserPlanningEntity.builder().id(1L).build());
        mockMvc.perform(
                        post("/api/user/planning/subject/1")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                CreateUserPlanningDTO.builder()
                                                        .semesterId(1L)
                                                        .build())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void createPlanningShouldValidateFields() throws Exception {
        mockMvc.perform(
                        post("/api/user/planning/subject/1")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.semesterId.length()").value(1L));
    }

    @Test
    public void shouldListPlanningFromUser() throws Exception {
        UserPlanningEntity planning = UserPlanningEntity.builder().id(1L).build();
        when(userPlanningService.findPlanningByUser("123")).thenReturn(List.of(planning));
        mockMvc.perform(get("/api/user/planning").with(jwt().jwt(jwt -> jwt.subject("123"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(planning.getId()));
    }

    @Test
    public void shouldGetSpecificElement() throws Exception {
        UserPlanningEntity planning = UserPlanningEntity.builder().id(1L).build();
        when(userPlanningService.findPlanningElementOrError("123", 1L)).thenReturn(planning);
        mockMvc.perform(
                        get("/api/user/planning/subject/1")
                                .with(jwt().jwt(jwt -> jwt.subject("123"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void shouldDeletePlanningElement() throws Exception {
        doNothing().when(userPlanningService).deletePlanningElement("123", 1L);
        mockMvc.perform(
                        get("/api/user/planning/subject/1")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
