package com.borathings.borapagar.user;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebMvc
public class UserControllerTests {
    @Autowired private MockMvc mockMvc;
    @MockBean private UserService userService;

    private UserEntity user;

    @BeforeEach
    public void setUp() {

        user =
                UserEntity.builder()
                        .id(1L)
                        .name("Isaac")
                        .email("isaac.lourenco.704@ufrn.edu.br")
                        .imageUrl("https://lindao.com")
                        .googleId("googleId")
                        .build();

        when(userService.findByGoogleIdOrError("googleId")).thenReturn(user);
    }

    @Test
    public void shouldGetAuthenticatedUser() throws Exception {
        this.mockMvc
                .perform(get("/api/users/me").with(jwt().jwt(jwt -> jwt.subject("googleId"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.imageUrl").value(user.getImageUrl()));
    }
}
