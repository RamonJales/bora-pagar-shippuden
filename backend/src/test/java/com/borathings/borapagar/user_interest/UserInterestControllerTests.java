package com.borathings.borapagar.user_interest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.borathings.borapagar.user_interest.dto.CreateUserInterestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserInterestController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebMvc
public class UserInterestControllerTests {
    @MockBean UserInterestService userInterestService;
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    public void shouldCreateInterest() throws Exception {
        when(userInterestService.create(eq("123"), eq(1L), any()))
                .thenReturn(UserInterestEntity.builder().id(1L).build());
        mockMvc.perform(
                        post("/api/user/interest/subject/1")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                CreateUserInterestDTO.builder()
                                                        .semesterId(1L)
                                                        .build())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void createInterestShouldValidateFields() throws Exception {
        mockMvc.perform(
                        post("/api/user/interest/subject/1")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.semesterId.length()").value(1L));
    }
}
