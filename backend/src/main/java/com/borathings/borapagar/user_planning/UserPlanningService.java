package com.borathings.borapagar.user_planning;

import com.borathings.borapagar.subject.SubjectEntity;
import com.borathings.borapagar.subject.SubjectService;
import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserService;
import com.borathings.borapagar.user_planning.dto.CreateUserPlanningDTO;
import com.borathings.borapagar.user_semester.UserSemesterEntity;
import com.borathings.borapagar.user_semester.UserSemesterService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UserPlanningService {
    @Autowired UserService userService;
    @Autowired UserSemesterService userSemesterService;
    @Autowired SubjectService subjectService;
    @Autowired UserPlanningRepository userPlanningRepository;

    /**
     * Adiciona uma disciplina no planejamento da grade do usuário.
     *
     * @param userGoogleId - String - Google id do usuário
     * @param subjectId - Long - ID da disciplina que será adicionada
     * @param interestDto - CreateUserInterestDTO - DTO com informações detalhando o semestre em que
     *     o usuário pretente pagar a disciplina
     * @return UserPlanningDto - Entidade de planejamento criada
     * @throws DuplicateKeyException - Caso usuário já tenha adicionado a disciplina no planejamento
     */
    public UserPlanningEntity create(
            String userGoogleId, Long subjectId, CreateUserPlanningDTO planningDto) {
        UserEntity user = userService.findByGoogleIdOrError(userGoogleId);
        SubjectEntity subject = subjectService.findByIdOrError(subjectId);
        Optional<UserPlanningEntity> planning =
                userPlanningRepository.findByUserIdAndSubjectId(user.getId(), subjectId);
        if (planning.isPresent()) {
            throw new DuplicateKeyException("Usuário já colocou esta disciplina no planejamento");
        }
        UserSemesterEntity userSemester =
                userSemesterService.findByIdAndValidatePermissions(
                        userGoogleId, planningDto.getSemesterId());
        UserPlanningEntity newInterest =
                UserPlanningEntity.builder()
                        .user(user)
                        .userSemester(userSemester)
                        .subject(subject)
                        .build();
        return userPlanningRepository.save(newInterest);
    }

    /**
     * Retorna todos os elementos do planejamento do usuário
     *
     * @param userGoogleId - String - Google ID do usuário.
     * @return List<UserPlanningEntity> - Lista de elementos do planejamento do usuário
     */
    public List<UserPlanningEntity> findPlanningByUser(String userGoogleId) {
        return userPlanningRepository.findByUser_GoogleId(userGoogleId);
    }

    /**
     * Retorna informações sobre um elemento do planejamento do usuário. Identificado pelo google id
     * do usuário e id da disciplina. Retorna erro caso o elemento não seja encontrado.
     *
     * @param userGoogleId - String - Google id do usuário
     * @param subjectId - Long - Id da disciplina
     * @return UserPlanningEntity - Entidade com informações sobre o elemento do planejamento
     * @throws EntityNotFoundException - Elemento não foi encontrado
     */
    public UserPlanningEntity findPlanningElementOrError(String userGoogleId, Long subjectId) {
        return userPlanningRepository
                .findByUser_GoogleIdAndSubjectId(userGoogleId, subjectId)
                .orElseThrow(
                        () ->
                                new EntityNotFoundException(
                                        "Elemento do planejamento não encontrado"));
    }

    /**
     * Remove um elemento do planejamento do usuário
     *
     * @param userGoogleId - String - Google id do usuário
     * @param subjectId - Long - Id da disciplina
     */
    public void deletePlanningElement(String userGoogleId, Long subjectId) {
        UserPlanningEntity planning = findPlanningElementOrError(userGoogleId, subjectId);
        userPlanningRepository.deleteById(planning.getId());
    }
}
