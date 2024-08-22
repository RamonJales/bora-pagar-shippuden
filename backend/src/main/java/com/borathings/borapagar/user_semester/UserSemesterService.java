package com.borathings.borapagar.user_semester;

import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class UserSemesterService {
    @Autowired private UserSemesterRepository userSemesterRepository;
    @Autowired private UserService userService;

    /**
     * Verifica se o usuário autenticado tem permissão para manipular um semestre de usuário.
     *
     * @param id - Long - Id do semestre
     * @param userGoogleId - String - Google ID do usuário
     * @throws AccessDeniedException - Quando o usuário não tem permissão para alterar o semestre
     */
    private void checkUserPermission(Long id, String userGoogleId) {
        UserSemesterEntity databaseUserSemester = findByIdOrError(id);
        UserEntity authenticatedUser = userService.findByGoogleIdOrError(userGoogleId);

        if (!authenticatedUser.getId().equals(databaseUserSemester.getUser().getId())) {
            throw new AccessDeniedException("Você não tem permissão para atualizar o semestre.");
        }
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
     * @param userSemesterEntity - UserSemesterEntity - Dados do semestre do usuário
     * @return Semestre salvo
     */
    public UserSemesterEntity create(String googleId, UserSemesterEntity userSemesterEntity) {
        UserEntity authenticatedUser = userService.findByGoogleIdOrError(googleId);
        userSemesterEntity.setUser(authenticatedUser);
        return userSemesterRepository.save(userSemesterEntity);
    }

    /**
     * Busca um semestre pelo id. Lança uma exceção caso o semestre não seja encontrado
     *
     * @param id - Long - Id do semestre
     * @throws EntityNotFoundException se a disciplina não existir
     * @return Disciplina encontrada
     */
    public UserSemesterEntity findByIdOrError(Long id) {
        UserSemesterEntity userSemesterEntity =
                userSemesterRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "Semestre de usuário com id "
                                                        + id
                                                        + " não encontrada"));
        return userSemesterEntity;
    }

    /**
     * Atualiza os dados de um semestre do usuário autenticado
     *
     * @param id - Id do semestre
     * @param userGoogleId - Google ID do usuário
     * @param userSemesterEntity - Novos dado autenticados do semestre do usuário
     * @throws EntityNotFoundException se o semestre não existir
     * @throws DuplicateKeyException se o usuário está tentando atualizar o semestre para ano e
     *     período já existente
     * @return Semestre atualizado
     */
    public UserSemesterEntity update(
            Long id, String userGoogleId, UserSemesterEntity userSemesterEntity) throws Exception {
        this.checkUserPermission(id, userGoogleId);

        userSemesterEntity.setId(id);
        try {
            userSemesterRepository.save(userSemesterEntity);
        } catch (DuplicateKeyException exception) {
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
     * @param id - Id do semestre
     * @param userGoogleId - Google ID do usuário autenticado
     */
    public void delete(Long id, String useGoogleId) {
        this.checkUserPermission(id, useGoogleId);
        userSemesterRepository.softDeleteById(id);
    }
}
