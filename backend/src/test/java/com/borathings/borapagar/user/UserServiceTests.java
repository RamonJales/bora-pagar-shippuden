package com.borathings.borapagar.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
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
                UserEntity.builder()
                        .email(oidcUser.getEmail())
                        .name(oidcUser.getFullName())
                        .googleId(oidcUser.getSubject())
                        .imageUrl(oidcUser.getPicture())
                        .build();

        verify(userRepository, times(1)).save(eq(createdUser));
    }

    @Test
    public void testUpsertUpdateUserWhenItExists() {
        OidcUser oidcUser = mock(OidcUser.class);
        when(oidcUser.getEmail()).thenReturn("carlos@email.com");
        when(oidcUser.getFullName()).thenReturn("Carlos");
        when(oidcUser.getPicture()).thenReturn("updatedImg.jpeg");
        when(oidcUser.getSubject()).thenReturn("1234");

        UserEntity existingUser =
                UserEntity.builder()
                        .email("carlos@email.com")
                        .name("Carlos")
                        .googleId("1234")
                        .imageUrl("img.jpeg")
                        .build();

        when(userRepository.findByGoogleId(eq("1234"))).thenReturn(Optional.of(existingUser));

        assertEquals("img.jpeg", existingUser.getImageUrl());

        userService.upsertFromOidcUser(oidcUser);

        assertEquals("updatedImg.jpeg", existingUser.getImageUrl());

        verify(userRepository, times(1)).save(eq(existingUser));
    }

    @Test
    public void shouldFindByGoogleId() {
        UserEntity user = UserEntity.builder().googleId("1234").build();
        when(userRepository.findByGoogleId(eq("1234"))).thenReturn(Optional.of(user));
        UserEntity foundUser = userService.findByGoogleIdOrError("1234");
        assertEquals(user, foundUser);
    }

    @Test
    public void shouldThrowEntityNotFoundIfUserDoesNotExist() {
        when(userRepository.findByGoogleId(eq("1234"))).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class, () -> userService.findByGoogleIdOrError("1234"));
    }
}
