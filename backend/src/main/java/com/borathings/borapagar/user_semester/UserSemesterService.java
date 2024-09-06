package com.borathings.borapagar.user_semester;

import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserService;
import com.borathings.borapagar.user_semester.dto.request.CreateUserSemesterDTO;
import com.borathings.borapagar.user_semester.dto.request.UpdateUserSemesterDTO;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class UserSemesterService {
    @Autowired private UserSemesterRepository userSemesterRepository;
    @Autowired private UserService userService;

    /**
     * Busca um semestre pelo id e verifica se o usuário autenticado tem permissão para acessá-lo.
     * Lança uma exceção quando o semestre não existe ou o usuário não tem permissão.
     *
     * @param userGoogleId - String - Google ID do usuário
     * @param semesterId - Long - Id do semestre
     * @return UserSemesterEntity - Entidade do semestre
     * @throws EntityNotFoundException - Quando o semestre não existe
     * @throws AccessDeniedException - Quando o usuário não tem permissão para acessar o semestre
     */
    public UserSemesterEntity findByIdAndValidatePermissions(String userGoogleId, Long semesterId) {
        UserSemesterEntity userSemesterEntity =
                userSemesterRepository
                        .findById(semesterId)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "Semestre de usuário com id "
                                                        + semesterId
                                                        + " não encontrada"));

        UserEntity authenticatedUser = userService.findByGoogleIdOrError(userGoogleId);
        if (!authenticatedUser.getId().equals(userSemesterEntity.getUser().getId())) {
            throw new AccessDeniedException("Você não tem permissão para acessar este semestre.");
        }
        return userSemesterEntity;
    }

    /**
     * Busca os semestres relacionados ao usuário autenticado.
     *
     * @param googleId - String - O Google ID do usuário autenticado.
     * @return List<UserSemesterEntity> - Lista contendo todos os semestres cadastrados no usuário
     *     autenticado.
     */
    public List<UserSemesterEntity> findByAuthenticatedUser(String googleId) {
        UserEntity authenticatedUser = userService.findByGoogleIdOrError(googleId);
        return userSemesterRepository.findByUserId(authenticatedUser.id);
    }

    /**
     * Salva um novo semestre do usuário autenticado no banco de dados
     *
     * @param userSemesterDTO - UserSemesterDTO - Dados do semestre do usuário
     * @return Semestre salvo
     */
    public UserSemesterEntity create(String googleId, CreateUserSemesterDTO userSemesterDTO) {
        UserEntity authenticatedUser = userService.findByGoogleIdOrError(googleId);
        UserSemesterEntity userSemesterEntity =
                UserSemesterEntity.builder()
                        .year(userSemesterDTO.getYear())
                        .period(userSemesterDTO.getPeriod())
                        .user(authenticatedUser)
                        .build();

        try {
            userSemesterRepository.save(userSemesterEntity);
        } catch (DataIntegrityViolationException exception) {
            String output =
                    String.format(
                            "Não foi possível criar pois o semestre %d.%d já existe.",
                            userSemesterEntity.getYear(), userSemesterEntity.getPeriod());
            throw new DuplicateKeyException(output);
        }
        return userSemesterEntity;
    }

    /**
     * Atualiza os dados de um semestre do usuário autenticado
     *
     * @param semesterId - Id do semestre
     * @param userGoogleId - Google ID do usuário
     * @param userSemesterDTO - Novos dados autenticados do semestre do usuário
     * @throws EntityNotFoundException se o semestre não existir
     * @throws DuplicateKeyException se o usuário está tentando atualizar o semestre para ano e
     *     período já existente
     * @return Semestre atualizado
     */
    public UserSemesterEntity update(
            Long semesterId, String userGoogleId, UpdateUserSemesterDTO userSemesterDTO)
            throws DuplicateKeyException {
        UserSemesterEntity userSemesterEntity =
                findByIdAndValidatePermissions(userGoogleId, semesterId);
        userSemesterEntity.setYear(userSemesterDTO.getYear());
        userSemesterEntity.setPeriod(userSemesterDTO.getPeriod());

        try {
            userSemesterRepository.save(userSemesterEntity);
        } catch (DataIntegrityViolationException exception) {
            String output =
                    String.format(
                            "Não foi possível atualizar pois o semestre %d.%d já existe.",
                            userSemesterEntity.getYear(), userSemesterEntity.getPeriod());
            throw new DuplicateKeyException(output);
        }
        return userSemesterEntity;
    }

    /**
     * Deleta um semestre do usuário pelo id.
     *
     * @param semesterId - Id do semestre
     * @param userGoogleId - Google ID do usuário autenticado
     */
    public void delete(Long semesterId, String userGoogleId) {
        findByIdAndValidatePermissions(userGoogleId, semesterId);
        userSemesterRepository.softDeleteById(semesterId);
    }
}
