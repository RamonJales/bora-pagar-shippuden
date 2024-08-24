package com.borathings.borapagar.user_semester;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;

import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserService;
import com.borathings.borapagar.user_semester.dto.UserSemesterDTO;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class UserSemesterServiceTests {
    @MockBean private UserSemesterRepository userSemesterRepository;
    @MockBean private UserService userService;
    @Autowired public UserSemesterService userSemesterService;

    @Test
    public void shouldCreateUserSemester() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userSemesterRepository.save(any())).thenAnswer((invocation) -> invocation.getArgument(0));
        UserSemesterDTO userSemesterDTO = UserSemesterDTO.builder()
                .year(2024)
                .period(1)
                .build();
        UserSemesterEntity userSemesterEntity = userSemesterService.create("123", userSemesterDTO);
        assertEquals(userSemesterEntity.getUser(), user);
        assertEquals(userSemesterEntity.getYear(), userSemesterDTO.getYear());
        assertEquals(userSemesterEntity.getPeriod(), userSemesterDTO.getPeriod());
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
        UserSemesterEntity userSemester = UserSemesterEntity.builder().id(1L).build();
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.of(userSemester));
        assertEquals(userSemesterService.findByIdOrError(1L), userSemester);
    }

    @Test
    public void findByIdShouldThrowIfEntityDoesNotExist() {
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userSemesterService.findByIdOrError(1L));
    }

    @Test
    public void shouldUpdateSemester() throws Exception {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        UserSemesterEntity databaseSemesterEntity = UserSemesterEntity.builder().id(1L).user(user).year(2024).period(1).build();
        UserSemesterDTO newSemesterData = UserSemesterDTO.builder().year(2025).period(1).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.of(databaseSemesterEntity));
        when(userSemesterRepository.save(any())).thenAnswer((invocation) -> invocation.getArgument(0));
        UserSemesterEntity updatedSemester = userSemesterService.update(1L, "123", newSemesterData);
        assertEquals(updatedSemester.getYear(), newSemesterData.getYear());
        assertEquals(updatedSemester.getPeriod(), newSemesterData.getPeriod());
        assertEquals(updatedSemester.getUser(), user);
    }

    @Test
    public void shouldNotLetUpdateFromDifferentUser() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        UserSemesterEntity databaseSemesterEntity = UserSemesterEntity.builder().id(1L).user(user).year(2024).period(1).build();
        UserSemesterDTO newSemesterData = UserSemesterDTO.builder().year(2025).period(1).build();
        UserEntity differentUser = UserEntity.builder().id(2L).googleId("456").build();
        when(userService.findByGoogleIdOrError("456")).thenReturn(differentUser);
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.of(databaseSemesterEntity));
        assertThrows(AccessDeniedException.class, () -> userSemesterService.update(1L, "456", newSemesterData));
    }

    @Test
    public void updateShouldThrowIfEntityDoesNotExist() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        UserSemesterDTO newSemesterData = UserSemesterDTO.builder().year(2025).period(1).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userSemesterService.update(1L, "123", newSemesterData));
    }

    @Test
    public void shouldDeleteSemester() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        UserSemesterEntity databaseSemesterEntity = UserSemesterEntity.builder().id(1L).user(user).year(2024).period(1).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.of(databaseSemesterEntity));
        doNothing().when(userSemesterRepository).softDeleteById(1L);
        assertDoesNotThrow(() -> userSemesterService.delete(1L, "123"));
    }

    @Test
    public void shouldNotDeleteFromDifferentUser() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        UserSemesterEntity databaseSemesterEntity = UserSemesterEntity.builder().id(1L).user(user).year(2024).period(1).build();
        UserEntity differentUser = UserEntity.builder().id(2L).googleId("456").build();
        when(userService.findByGoogleIdOrError("456")).thenReturn(differentUser);
        when(userSemesterRepository.findById(1L)).thenReturn(Optional.of(databaseSemesterEntity));
        assertThrows(AccessDeniedException.class, () -> userSemesterService.delete(1L, "456"));
    }
}
