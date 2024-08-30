package com.borathings.borapagar.user_interest;

import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInterestService {
    @Autowired private UserInterestRepository userInterestRepository;
    @Autowired private UserService userService;

    /**
     * Encontra todos os interesses do usuário que não foram deletados.
     *
     * @param userGoogleId - String - Google ID do usuário.
     * @return List<UserInterestEntity> - Lista de interesses do usuário.
     */
    public List<UserInterestEntity> findInterestsByUser(String userGoogleId) {
        UserEntity user = userService.findByGoogleIdOrError(userGoogleId);
        return userInterestRepository.findByUserIdAndDeletedAtIsNull(user.getId());
    }
}
