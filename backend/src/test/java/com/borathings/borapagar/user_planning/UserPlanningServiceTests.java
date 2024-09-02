package com.borathings.borapagar.user_planning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.borathings.borapagar.subject.SubjectEntity;
import com.borathings.borapagar.subject.SubjectService;
import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserService;
import com.borathings.borapagar.user_planning.dto.CreateUserPlanningDTO;
import com.borathings.borapagar.user_semester.UserSemesterEntity;
import com.borathings.borapagar.user_semester.UserSemesterService;
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
}
