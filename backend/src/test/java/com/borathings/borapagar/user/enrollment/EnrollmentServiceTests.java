package com.borathings.borapagar.user.enrollment;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.borathings.borapagar.classroom.ClassroomEntity;
import com.borathings.borapagar.classroom.ClassroomService;
import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserRepository;
import com.borathings.borapagar.user.UserService;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;

@SpringBootTest
public class EnrollmentServiceTests {
    @MockBean UserRepository userRepository;
    @MockBean UserService userService;
    @MockBean ClassroomService classroomService;
    @Autowired EnrollmentService enrollmentService;

    @Test
    void shouldEnrollUserInClassroom() {
        UserEntity user =
                UserEntity.builder()
                        .googleId("123")
                        .name("Teste")
                        .email("teste@gmail.com")
                        .enrolledClassrooms(HashSet.newHashSet(0))
                        .build();
        ClassroomEntity classroom = ClassroomEntity.builder().id(1L).build();
        when(classroomService.findByIdOrError(1L)).thenReturn(classroom);
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        assertDoesNotThrow(() -> enrollmentService.enrollUserInClassroom("123", 1L));
    }

    @Test
    void shouldThrowExceptionWhenUserIsAlreadyEnrolledInClassroom() {
        ClassroomEntity classroom = ClassroomEntity.builder().id(1L).build();
        UserEntity user =
                UserEntity.builder()
                        .googleId("123")
                        .name("Teste")
                        .email("teste@gmail.com")
                        .enrolledClassrooms(Set.of(classroom))
                        .build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(classroomService.findByIdOrError(1L)).thenReturn(classroom);
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        assertThrows(
                DuplicateKeyException.class,
                () -> enrollmentService.enrollUserInClassroom("123", 1L));
    }
}
