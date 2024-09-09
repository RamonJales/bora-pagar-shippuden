package com.borathings.borapagar.user.enrollment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EnrollmentController.class)
public class EnrollmentControllerTests {
    @Autowired MockMvc mockMvc;
    @MockBean EnrollmentService enrollmentService;

    @Test
    void shouldEnrollUserInClassroom() throws Exception {
        doNothing().when(enrollmentService).enrollUserInClassroom("1234", 1L);
        doThrow(EntityNotFoundException.class)
                .when(enrollmentService)
                .enrollUserInClassroom(AdditionalMatchers.not(eq("1234")), any());
        mockMvc.perform(post("/api/user/enrollment/1").with(jwt().jwt(jwt -> jwt.subject("1234"))))
                .andExpect(status().isOk());
    }
}
