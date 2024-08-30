package com.borathings.borapagar.user_interest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserInterestServiceTests {
    @MockBean private UserInterestRepository userInterestRepository;
    @MockBean private UserService userService;
    @Autowired private UserInterestService userInterestService;

    @Test
    public void shouldListInterestsFromUser() {
        UserEntity user = UserEntity.builder().id(1L).build();
        when(userService.findByGoogleIdOrError("123")).thenReturn(user);
        when(userInterestRepository.findByUserIdAndDeletedAtIsNull(user.getId()))
                .thenReturn(List.of(UserInterestEntity.builder().id(1L).build()));

        List<UserInterestEntity> interests = userInterestService.findInterestsByUser("123");
        assertEquals(interests.get(0).getId(), 1L);
    }
}
