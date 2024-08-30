package com.borathings.borapagar.user_interest;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserInterestController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebMvc
public class UserInterestControllerTests {
    @Autowired private MockMvc mockMvc;
    @MockBean private UserInterestService userInterestService;

    @Test
    public void shouldGetSpecificInterest() throws Exception {
        UserInterestEntity interest = UserInterestEntity.builder().id(1L).build();
        when(userInterestService.getSpecificInterest("123", 1L)).thenReturn(interest);
        mockMvc.perform(
                        get("/api/user/interest/subject/1")
                                .with(jwt().jwt(jwt -> jwt.subject("123"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
