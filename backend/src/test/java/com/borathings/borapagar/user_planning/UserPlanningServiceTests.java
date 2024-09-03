package com.borathings.borapagar.user_planning;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.borathings.borapagar.subject.SubjectEntity;
import com.borathings.borapagar.subject.SubjectService;
import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserService;
import com.borathings.borapagar.user_planning.dto.CreateUserPlanningDTO;
import com.borathings.borapagar.user_planning.dto.UpdateUserPlanningDTO;
import com.borathings.borapagar.user_semester.UserSemesterEntity;
import com.borathings.borapagar.user_semester.UserSemesterService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;

@SpringBootTest
public class UserPlanningServiceTests {
    @MockBean UserService userService;
    @MockBean UserSemesterService userSemesterService;
    @MockBean SubjectService subjectService;
    @MockBean UserPlanningRepository userPlanningRepository;
    @Autowired UserPlanningService userPlanningService;

    @Test
    public void shouldCreateInterest() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        SubjectEntity subject = SubjectEntity.builder().id(1L).build();
        UserSemesterEntity userSemester = UserSemesterEntity.builder().id(1l).user(user).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(subjectService.findByIdOrError(1L)).thenReturn(subject);
        when(userPlanningRepository.findByUserIdAndSubjectId(1L, 1L)).thenReturn(Optional.empty());
        when(userSemesterService.findByIdAndValidatePermissions("123", 1L))
                .thenReturn(userSemester);
        when(userPlanningRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        UserPlanningEntity userPlanning =
                userPlanningService.create(
                        "123", 1L, CreateUserPlanningDTO.builder().semesterId(1L).build());
        assertEquals(userPlanning.getUser().getId(), user.getId());
        assertEquals(userPlanning.getUserSemester().getId(), userSemester.getId());
        assertEquals(userPlanning.getSubject().getId(), subject.getId());
    }

    @Test
    public void shouldThrowWhenDuplicateInterest() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        SubjectEntity subject = SubjectEntity.builder().id(1L).build();
        UserSemesterEntity userSemester = UserSemesterEntity.builder().id(1L).user(user).build();
        UserPlanningEntity userPlanning =
                UserPlanningEntity.builder()
                        .id(1L)
                        .user(user)
                        .subject(subject)
                        .userSemester(userSemester)
                        .build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(subjectService.findByIdOrError(1L)).thenReturn(subject);
        when(userPlanningRepository.findByUserIdAndSubjectId(1L, 1L))
                .thenReturn(Optional.of(userPlanning));
        when(userSemesterService.findByIdAndValidatePermissions("123", 1L))
                .thenReturn(userSemester);

        assertThrows(
                DuplicateKeyException.class,
                () ->
                        userPlanningService.create(
                                "123", 1L, CreateUserPlanningDTO.builder().semesterId(1L).build()));
    }

    @Test
    public void shouldListPlanningFromUser() {
        when(userPlanningRepository.findByUser_GoogleId("123"))
                .thenReturn(List.of(UserPlanningEntity.builder().id(1L).build()));

        List<UserPlanningEntity> plannings = userPlanningService.findPlanningByUser("123");
        assertEquals(plannings.get(0).getId(), 1L);
    }

    @Test
    public void getSpecificPlanningShouldThrowIfNotFound() {
        when(userPlanningRepository.findByUser_GoogleIdAndSubjectId("123", 1L))
                .thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> userPlanningService.findPlanningElementOrError("123", 1L));
    }

    @Test
    public void shouldGetSpecificPlanning() {
        UserPlanningEntity planning = UserPlanningEntity.builder().id(1L).build();
        when(userPlanningRepository.findByUser_GoogleIdAndSubjectId("123", 1L))
                .thenReturn(Optional.of(planning));
        UserPlanningEntity result = userPlanningService.findPlanningElementOrError("123", 1L);
        assertEquals(result.getId(), planning.getId());
    }

    @Test
    public void shouldUpdateSemesterFromPlanning() {
        UserPlanningEntity userPlanning = UserPlanningEntity.builder().id(1L).build();
        when(userPlanningRepository.findByUser_GoogleIdAndSubjectId("123", 1L))
                .thenReturn(Optional.of(userPlanning));
        when(userSemesterService.findByIdAndValidatePermissions("123", 2L))
                .thenReturn(UserSemesterEntity.builder().id(2L).build());
        when(userPlanningRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserPlanningEntity updatedPlanning =
                userPlanningService.updatePlanningSemester(
                        "123", 1L, UpdateUserPlanningDTO.builder().semesterId(2L).build());
        assertEquals(updatedPlanning.getUserSemester().getId(), 2L);
		}

		@Test
    public void shouldDeletePlanningElement() {
        UserPlanningEntity planning = UserPlanningEntity.builder().id(1L).build();
        doNothing().when(userPlanningRepository).deleteById(1L);
        when(userPlanningRepository.findByUser_GoogleIdAndSubjectId("123", 1L))
                .thenReturn(Optional.of(planning));
        assertDoesNotThrow(() -> userPlanningService.deletePlanningElement("123", 1L));
    }

    @Test
    public void deletePlanningElementShouldThrowIfNotFound() {
        when(userPlanningRepository.findByUser_GoogleIdAndSubjectId("123", 1L))
                .thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> userPlanningService.deletePlanningElement("123", 1L));
    }
}
