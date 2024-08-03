package com.borathings.borapagar.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class UserServiceTests {

    @InjectMocks UserService userService;

    @Mock UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpsertInsertUserWhenItDoesNotExist() {
        OidcUser oidcUser = mock(OidcUser.class);
        when(oidcUser.getEmail()).thenReturn("carlos@email.com");
        when(oidcUser.getFullName()).thenReturn("Carlos");
        when(oidcUser.getPicture()).thenReturn("img.jpeg");
        when(oidcUser.getSubject()).thenReturn("1234");

        when(userRepository.findByGoogleId(eq("1234"))).thenReturn(Optional.empty());

        userService.upsertFromOidcUser(oidcUser);

        UserEntity createdUser =
                new UserEntity(
                        oidcUser.getEmail(),
                        oidcUser.getFullName(),
                        oidcUser.getSubject(),
                        oidcUser.getPicture());

        verify(userRepository, times(1)).save(eq(createdUser));
    }

    @Test
    public void testUpsertUpdateUserWhenItExists() {
        OidcUser oidcUser = mock(OidcUser.class);
        when(oidcUser.getEmail()).thenReturn("carlos@email.com");
        when(oidcUser.getFullName()).thenReturn("Carlos");
        when(oidcUser.getPicture()).thenReturn("updatedImg.jpeg");
        when(oidcUser.getSubject()).thenReturn("1234");

        UserEntity existingUser = new UserEntity("carlos@email.com", "Carlos", "1234", "img.jpeg");

        when(userRepository.findByGoogleId(eq("1234"))).thenReturn(Optional.of(existingUser));

        assertEquals("img.jpeg", existingUser.getImageUrl());

        userService.upsertFromOidcUser(oidcUser);

        assertEquals("updatedImg.jpeg", existingUser.getImageUrl());

        verify(userRepository, times(1)).save(eq(existingUser));
    }
}
