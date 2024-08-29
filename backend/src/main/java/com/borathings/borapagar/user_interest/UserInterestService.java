package com.borathings.borapagar.user_interest;

import com.borathings.borapagar.core.Patcher;
import com.borathings.borapagar.core.exception.ApiException;
import com.borathings.borapagar.subject.SubjectEntity;
import com.borathings.borapagar.subject.SubjectService;
import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserService;
import com.borathings.borapagar.user_interest.dto.CreateUserInterestDTO;
import com.borathings.borapagar.user_interest.dto.UpdateUserInterestDTO;
import com.borathings.borapagar.user_semester.UserSemesterEntity;
import com.borathings.borapagar.user_semester.UserSemesterService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserInterestService {
    @Autowired UserService userService;
    @Autowired SubjectService subjectService;
    @Autowired UserSemesterService userSemesterService;
    @Autowired UserInterestRepository userSubjectInterestRepository;
    @Autowired Patcher<UserInterestEntity> patcher;

    /**
     * Cria um interesse do usuário (identificado pelo google id) na disciplina escolhida. O usuário
     * informa o id do semestre em que deseja cursar a disciplina. Este método valida se as
     * entidades envolvidas existem e checa se o semestre informado pertence ao usuário.
     *
     * @param userGoogleId - String - google id do usuário autenticado
     * @param subjectId - Long - Id da disciplina
     * @param interestDto - CreateUserInterestDTO - Dados do interesse informados
     * @return UserInterestEntity - Entidade de interesse criado
     * @throws DuplicateKeyException - Caso o usuário já tenha demonstrado interesse na disciplina
     */
    public UserInterestEntity create(
            String userGoogleId, Long subjectId, CreateUserInterestDTO interestDto) {
        UserEntity user = userService.findByGoogleIdOrError(userGoogleId);
        SubjectEntity subject = subjectService.findByIdOrError(subjectId);
        UserSemesterEntity userSemester =
                userSemesterService.findByIdAndValidatePermissions(
                        userGoogleId, interestDto.getUserSemesterId());
        UserInterestEntity interest =
                UserInterestEntity.builder()
                        .user(user)
                        .subject(subject)
                        .userSemester(userSemester)
                        .build();
        try {
            userSubjectInterestRepository.save(interest);
        } catch (DataIntegrityViolationException ex) {
            String output =
                    String.format("Você já possui interesse na disciplina %s", subject.getName());
            throw new DuplicateKeyException(output);
        }

        return interest;
    }

    /**
     * Busca um interesse do usuário autenticado na disciplina escolhida. Retorna um erro se o
     * usuário ainda não demonstrou interesse na disciplina.
     *
     * @param userGoogleId - String - google id do usuário autenticado
     * @param subjectId - Long - Id da disciplina
     * @return UserInterestEntity - Entidade de interesse encontrada
     * @throws EntityNotFoundException - Caso o usuário ainda não demonstrou interesse na disciplina
     */
    public UserInterestEntity findByUserGoogleIdAndSubjectIdOrError(
            String userGoogleId, Long subjectId) {
        UserEntity user = userService.findByGoogleIdOrError(userGoogleId);
        return userSubjectInterestRepository
                .findByUserIdAndSubjectId(user.getId(), subjectId)
                .orElseThrow(
                        () ->
                                new EntityNotFoundException(
                                        "Você ainda não demonstrou interesse nesta disciplina"));
    }

    /**
     * Retorna todos os interesses do usuário com o google id informado.
     *
     * @param userGoogleId - String - google id do usuário.
     * @return List<UserInterestEntity> - Lista de interesses do usuário.
     */
    public List<UserInterestEntity> findAllByUserGoogleId(String userGoogleId) {
        UserEntity user = userService.findByGoogleIdOrError(userGoogleId);
        return userSubjectInterestRepository.findByUserId(user.getId());
    }

    /**
     * Atualiza um interesse do usuário na disciplina escolhida. Os campos nulos no DTO serão
     * ignorados na atualização.
     *
     * @param userGoogleId - String - google id do usuário
     * @param subjectId - Long - Id da disciplina
     * @param interestDto - UpdateUserInterestDTO - Dados do interesse a serem atualizados
     * @return UserInterestEntity - Entidade de interesse atualizada
     */
    public UserInterestEntity partialUpdate(
            String userGoogleId, Long subjectId, UpdateUserInterestDTO interestDto) {
        UserInterestEntity interest =
                findByUserGoogleIdAndSubjectIdOrError(userGoogleId, subjectId);
        UserInterestEntity updateInterestData = interestDto.toEntity();
        if (interestDto.getUserSemesterId() != null) {
            updateInterestData.setUserSemester(
                    userSemesterService.findByIdAndValidatePermissions(
                            userGoogleId, interestDto.getUserSemesterId()));
        }
        try {
            patcher.patch(interest, updateInterestData);
        } catch (IllegalAccessException ex) {
            throw new ApiException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao atualizar interesse", ex);
        }
        return userSubjectInterestRepository.save(interest);
    }

    /**
     * Apaga o interesse do usuário na disciplina escolhida.
     *
     * @param userGoogleId - String - google id do usuário
     * @param subjectId - Long - Id da disciplina
     */
    public void delete(String userGoogleId, Long subjectId) {
        UserInterestEntity interest =
                findByUserGoogleIdAndSubjectIdOrError(userGoogleId, subjectId);
        userSubjectInterestRepository.softDeleteById(interest.getId());
    }
}
