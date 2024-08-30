package com.borathings.borapagar.user_interest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserInterestServiceTests {
    @MockBean private UserService userService;
    @MockBean private UserInterestRepository userInterestRepository;
    @Autowired private UserInterestService userInterestService;

    @Test
    public void shouldGetSpecificInterest() {
        UserEntity user = UserEntity.builder().id(1L).build();
        UserInterestEntity interest = UserInterestEntity.builder().id(1L).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userInterestRepository.findByUserIdAndSubjectId(1L, 1L))
                .thenReturn(Optional.of(interest));
        UserInterestEntity result = userInterestService.getSpecificInterest("123", 1L);
        assertEquals(result.getId(), interest.getId());
    }

    @Test
    public void getSpecificInterestShouldThrowIfDeleted() {
        UserEntity user = UserEntity.builder().id(1L).build();
        UserInterestEntity interest =
                UserInterestEntity.builder().id(1L).deletedAt(LocalDateTime.now()).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userInterestRepository.findByUserIdAndSubjectId(1L, 1L))
                .thenReturn(Optional.of(interest));
        assertThrows(
                EntityNotFoundException.class,
                () -> userInterestService.getSpecificInterest("123", 1L));
    }

    @Test
    public void getSpecificInterestShouldThrowIfNotFound() {
        UserEntity user = UserEntity.builder().id(1L).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userInterestRepository.findByUserIdAndSubjectId(1L, 1L)).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> userInterestService.getSpecificInterest("123", 1L));
    }
}
