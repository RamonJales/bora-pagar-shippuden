package com.borathings.borapagar.user_interest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserInterestService {
    @Autowired private UserInterestRepository userInterestRepository;
    @Autowired private UserService userService;

    /**
     * Encontra um interesse pelo id do usuário e pela disciplina.
     * Retorna um erro se este interesse não existir.
     * @param userId - Long - Id do usuário
     * @param subjectId - Long - Id da disciplina
     * @return UserInterestEntity - Entidade com informações sobre o interesse do usuário nesta disciplina
     * @throws EntityNotFoundException - Interesse não foi encontrado
     */
    private UserInterestEntity findByUserIdAndSubjectIdOrError(Long userId, Long subjectId) {
        return userInterestRepository.findByUserIdAndSubjectId(userId, subjectId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não demonstrou interesse nesta disciplina"));
    }

    /**
     * Retorna informações sobre um interesse específico do usuário (identificado pelo google id)
     * Este método apenas retorna se o interesse existir e não tiver sido marcado como deletado.
     * @param userGoogleId - String - Google id do usuário
     * @param subjectId - Long - Id da disciplina que o usuário manifestou interesse
     * @return UserInterestEntity - Entidade com informações sobre o interesse do usuário nesta disciplina
     * @throws EntityNotFoundException - Usuário não demonstrou (ou deletou) interesse nesta disciplina
     */
    public UserInterestEntity getSpecificInterest(String userGoogleId, Long subjectId) {
        UserEntity user = userService.findByGoogleIdOrError(userGoogleId);
        UserInterestEntity userInterest = findByUserIdAndSubjectIdOrError(user.getId(), subjectId);
        if(userInterest.getDeletedAt() != null) {
            throw new EntityNotFoundException("Usuário não demonstrou interesse nesta disciplina");
        }
        return userInterest;
    }
}
