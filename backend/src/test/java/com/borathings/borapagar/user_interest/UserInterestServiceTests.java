package com.borathings.borapagar.user_interest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.borathings.borapagar.subject.SubjectEntity;
import com.borathings.borapagar.subject.SubjectService;
import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserService;
import com.borathings.borapagar.user_interest.dto.CreateUserInterestDTO;
import com.borathings.borapagar.user_semester.UserSemesterEntity;
import com.borathings.borapagar.user_semester.UserSemesterService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;

@SpringBootTest
public class UserInterestServiceTests {
    @MockBean UserService userService;
    @MockBean UserSemesterService userSemesterService;
    @MockBean SubjectService subjectService;
    @MockBean UserInterestRepository userInterestRepository;
    @Autowired UserInterestService userInterestService;

    @Test
    public void shouldCreateInterest() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        SubjectEntity subject = SubjectEntity.builder().id(1L).build();
        UserSemesterEntity userSemester = UserSemesterEntity.builder().id(1l).user(user).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(subjectService.findByIdOrError(1L)).thenReturn(subject);
        when(userInterestRepository.findByUserIdAndSubjectId(1L, 1L)).thenReturn(Optional.empty());
        when(userSemesterService.findByIdAndValidatePermissions("123", 1L))
                .thenReturn(userSemester);
        when(userInterestRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        UserInterestEntity userInterest =
                userInterestService.create(
                        "123", 1L, CreateUserInterestDTO.builder().semesterId(1L).build());
        assertEquals(userInterest.getUser().getId(), user.getId());
        assertEquals(userInterest.getUserSemester().getId(), userSemester.getId());
        assertEquals(userInterest.getSubject().getId(), subject.getId());
    }

    @Test
    public void shouldRecreateIfDeleted() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        SubjectEntity subject = SubjectEntity.builder().id(1L).build();
        UserSemesterEntity userSemester1 = UserSemesterEntity.builder().id(1L).user(user).build();
        UserInterestEntity userInterest =
                UserInterestEntity.builder()
                        .id(1L)
                        .user(user)
                        .subject(subject)
                        .userSemester(userSemester1)
                        .deletedAt(LocalDateTime.now())
                        .build();
        UserSemesterEntity userSemester2 = UserSemesterEntity.builder().id(2L).user(user).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(subjectService.findByIdOrError(1L)).thenReturn(subject);
        when(userInterestRepository.findByUserIdAndSubjectId(1L, 1L))
                .thenReturn(Optional.of(userInterest));
        when(userSemesterService.findByIdAndValidatePermissions("123", 2L))
                .thenReturn(userSemester2);
        when(userInterestRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        UserInterestEntity createdUserInterest =
                userInterestService.create(
                        "123", 1L, CreateUserInterestDTO.builder().semesterId(2L).build());
        assertEquals(createdUserInterest.getUser().getId(), user.getId());
        assertEquals(createdUserInterest.getUserSemester().getId(), userSemester2.getId());
        assertEquals(createdUserInterest.getSubject().getId(), subject.getId());
        assertNull(createdUserInterest.getDeletedAt());
    }

    @Test
    public void shouldThrowWhenDuplicateInterest() {
        UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
        SubjectEntity subject = SubjectEntity.builder().id(1L).build();
        UserSemesterEntity userSemester = UserSemesterEntity.builder().id(1L).user(user).build();
        UserInterestEntity userInterest =
                UserInterestEntity.builder()
                        .id(1L)
                        .user(user)
                        .subject(subject)
                        .userSemester(userSemester)
                        .build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(subjectService.findByIdOrError(1L)).thenReturn(subject);
        when(userInterestRepository.findByUserIdAndSubjectId(1L, 1L))
                .thenReturn(Optional.of(userInterest));
        when(userSemesterService.findByIdAndValidatePermissions("123", 1L))
                .thenReturn(userSemester);

        assertThrows(
                DuplicateKeyException.class,
                () ->
                        userInterestService.create(
                                "123", 1L, CreateUserInterestDTO.builder().semesterId(1L).build()));
    }
}
