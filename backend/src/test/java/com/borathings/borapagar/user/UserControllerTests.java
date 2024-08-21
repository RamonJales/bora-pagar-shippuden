package com.borathings.borapagar.user;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;

import com.borathings.borapagar.utils.AuthenticatedMockMvc;

@WebMvcTest(UserController.class)
@Import(AuthenticatedMockMvc.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebMvc
public class UserControllerTests {
    @Autowired private MockMvc mockMvc;
    private UserEntity user;

    private OidcUser oidcUser;

    @BeforeEach
    public void setUp() {
        user =
                UserEntity.builder()
                        .id(1L)
                        .name("Isaac")
                        .email("isaac.lourenco.704@ufrn.edu.br")
                        .imageUrl("https://lindao.com")
                        .googleId("googleId")
                        .deleted(false)
                        .build();

        oidcUser = mock(OidcUser.class);
        when(oidcUser.getEmail()).thenReturn(user.getEmail());
        when(oidcUser.getFullName()).thenReturn(user.getName());
        when(oidcUser.getPicture()).thenReturn(user.getImageUrl());
        when(oidcUser.getSubject()).thenReturn(user.getGoogleId());
    }
    
    @Test
    public void shouldGetAuthenticatedUser() throws Exception {
        this.mockMvc
                .perform(get("/api/users/me").with(jwt().jwt(jwt -> jwt.subject(user.getGoogleId()))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.imageUrl").value(user.getImageUrl()));
    }
}