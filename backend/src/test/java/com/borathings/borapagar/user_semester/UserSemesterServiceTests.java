package com.borathings.borapagar.user_semester;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserService;
import com.borathings.borapagar.user_semester.dto.request.CreateUserSemesterDTO;
import com.borathings.borapagar.user_semester.dto.request.UpdateUserSemesterDTO;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;

@SpringBootTest
public class UserSemesterServiceTests {
    @MockBean private UserSemesterRepository userSemesterRepository;
    @MockBean private UserService userService;
    @Autowired public UserSemesterService userSemesterService;

    @Test
    public void shouldCreateUserSemester() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userSemesterRepository.save(any()))
                .thenAnswer((invocation) -> invocation.getArgument(0));
        CreateUserSemesterDTO userSemesterDTO =
                CreateUserSemesterDTO.builder().year(2024).period(1).build();
        UserSemesterEntity userSemesterEntity = userSemesterService.create("123", userSemesterDTO);
        assertEquals(userSemesterEntity.getUser(), user);
        assertEquals(userSemesterEntity.getYear(), userSemesterDTO.getYear());
        assertEquals(userSemesterEntity.getPeriod(), userSemesterDTO.getPeriod());
    }

    @Test
    public void createShouldNotAllowDuplicates() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userSemesterRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(
                DuplicateKeyException.class,
                () ->
                        userSemesterService.create(
                                "123",
                                CreateUserSemesterDTO.builder().year(2024).period(1).build()));
    }

    @Test
    public void shouldFindByAuthenticatedUser() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        UserSemesterEntity userSemester = UserSemesterEntity.builder().id(1L).user(user).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userSemesterRepository.findByUserId(1L)).thenReturn(List.of(userSemester));
        assertEquals(userSemesterService.findByAuthenticatedUser("123"), List.of(userSemester));
    }

    @Test
    public void shouldFindById() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        UserSemesterEntity userSemester = UserSemesterEntity.builder().id(1L).user(user).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.of(userSemester));
        assertEquals(userSemesterService.findByIdAndValidatePermissions("123", 1L), userSemester);
    }

    @Test
    public void findByIdShouldThrowIfEntityDoesNotExist() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> userSemesterService.findByIdAndValidatePermissions("123", 1L));
    }

    @Test
    public void findByIdShouldThrowIfDifferentUser() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        UserEntity differentUser = UserEntity.builder().id(2L).googleId("456").build();
        UserSemesterEntity userSemester = UserSemesterEntity.builder().id(1L).user(user).build();
        when(userService.findByGoogleIdOrError("456")).thenReturn(differentUser);
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.of(userSemester));
        assertThrows(
                AccessDeniedException.class,
                () -> userSemesterService.findByIdAndValidatePermissions("456", 1L));
    }

    @Test
    public void shouldUpdateSemester() throws Exception {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        UserSemesterEntity databaseSemesterEntity =
                UserSemesterEntity.builder().id(1L).user(user).year(2024).period(1).build();
        UpdateUserSemesterDTO newSemesterData =
                UpdateUserSemesterDTO.builder().year(2025).period(1).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.of(databaseSemesterEntity));
        when(userSemesterRepository.save(any()))
                .thenAnswer((invocation) -> invocation.getArgument(0));
        UserSemesterEntity updatedSemester = userSemesterService.update(1L, "123", newSemesterData);
        assertEquals(updatedSemester.getYear(), newSemesterData.getYear());
        assertEquals(updatedSemester.getPeriod(), newSemesterData.getPeriod());
        assertEquals(updatedSemester.getUser(), user);
    }

    @Test
    public void shouldNotLetUpdateFromDifferentUser() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        UserSemesterEntity databaseSemesterEntity =
                UserSemesterEntity.builder().id(1L).user(user).year(2024).period(1).build();
        UpdateUserSemesterDTO newSemesterData =
                UpdateUserSemesterDTO.builder().year(2025).period(1).build();
        UserEntity differentUser = UserEntity.builder().id(2L).googleId("456").build();
        when(userService.findByGoogleIdOrError("456")).thenReturn(differentUser);
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.of(databaseSemesterEntity));
        assertThrows(
                AccessDeniedException.class,
                () -> userSemesterService.update(1L, "456", newSemesterData));
    }

    @Test
    public void updateShouldNotAllowDuplicates() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        UserSemesterEntity userSemester =
                UserSemesterEntity.builder().id(1L).year(2024).period(1).user(user).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.of(userSemester));
        when(userSemesterRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
        assertThrows(
                DuplicateKeyException.class,
                () ->
                        userSemesterService.update(
                                1L,
                                "123",
                                UpdateUserSemesterDTO.builder().year(2024).period(1).build()));
    }

    @Test
    public void updateShouldThrowIfEntityDoesNotExist() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        UpdateUserSemesterDTO newSemesterData =
                UpdateUserSemesterDTO.builder().year(2025).period(1).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> userSemesterService.update(1L, "123", newSemesterData));
    }

    @Test
    public void shouldDeleteSemester() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        UserSemesterEntity databaseSemesterEntity =
                UserSemesterEntity.builder().id(1L).user(user).year(2024).period(1).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.of(databaseSemesterEntity));
        doNothing().when(userSemesterRepository).softDeleteById(1L);
        assertDoesNotThrow(() -> userSemesterService.delete(1L, "123"));
    }

    @Test
    public void shouldNotDeleteFromDifferentUser() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        UserSemesterEntity databaseSemesterEntity =
                UserSemesterEntity.builder().id(1L).user(user).year(2024).period(1).build();
        UserEntity differentUser = UserEntity.builder().id(2L).googleId("456").build();
        when(userService.findByGoogleIdOrError("456")).thenReturn(differentUser);
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.of(databaseSemesterEntity));
        assertThrows(AccessDeniedException.class, () -> userSemesterService.delete(1L, "456"));
    }
}
