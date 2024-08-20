package com.borathings.borapagar.user;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;

import com.borathings.borapagar.utils.AuthenticatedMockMvc;

@WebMvcTest(UserController.class)
@Import(AuthenticatedMockMvc.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebMvc
public class UserControllerTests {
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
    
}