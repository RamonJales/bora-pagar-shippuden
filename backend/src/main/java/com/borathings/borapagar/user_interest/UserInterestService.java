package com.borathings.borapagar.user_interest;

import com.borathings.borapagar.subject.SubjectEntity;
import com.borathings.borapagar.subject.SubjectService;
import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserService;
import com.borathings.borapagar.user_interest.dto.CreateUserInterestDTO;
import com.borathings.borapagar.user_semester.UserSemesterEntity;
import com.borathings.borapagar.user_semester.UserSemesterService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UserInterestService {
    @Autowired UserService userService;
    @Autowired UserSemesterService userSemesterService;
    @Autowired SubjectService subjectService;
    @Autowired UserInterestRepository userInterestRepository;

    /**
     * Recria a entidade de interesse, atualizando seus dados, caso ela tenha sido deletada. Retorna
     * um erro se a entidadde ainda não foi deletada.
     *
     * @param userGoogleId - String - Google id do usuário que possui este interesse
     * @param interest - UserInterestEntity - Entidade de interesse deletada
     * @param interestDto - CreateUserInterestDTO - DTO com os dados de criação do interesse
     * @return UserInterestEntity - Entidade de interesse recriada
     * @throws DuplicateKeyException - Caso o interesse ainda não foi deletado
     */
    private UserInterestEntity recreateIfDeleted(
            String userGoogleId, UserInterestEntity interest, CreateUserInterestDTO interestDto) {
        UserSemesterEntity userSemester =
                userSemesterService.findByIdAndValidatePermissions(
                        userGoogleId, interestDto.getSemesterId());
        if (interest.getDeletedAt() == null) {
            throw new DuplicateKeyException("Usuário já demonstrou interesse nesta disciplina");
        }
        interest.setDeletedAt(null);
        interest.setUserSemester(userSemester);
        return userInterestRepository.save(interest);
    }

    /**
     * Cria o interesse do usuário correspondende na disciplina especificada. Esse método garante
     * que só tenha um interesse por par (usuário, disciplina), atualizando o interesse caso ele
     * tenha sido deletado.
     *
     * @param userGoogleId - String - Google id do usuário que possui este interesse
     * @param subjectId - Long - ID da disciplina que o usuário se interessa
     * @param interestDto - CreateUserInterestDTO - DTO com os dados de criação do interesse
     * @return UserInterestEntity - Entidade de interesse criada
     * @throws DuplicateKeyException - Caso o interesse já exista e não foi deletado
     */
    public UserInterestEntity create(
            String userGoogleId, Long subjectId, CreateUserInterestDTO interestDto) {
        UserEntity user = userService.findByGoogleIdOrError(userGoogleId);
        SubjectEntity subject = subjectService.findByIdOrError(subjectId);
        Optional<UserInterestEntity> interest =
                userInterestRepository.findByUserIdAndSubjectId(user.getId(), subjectId);
        if (!interest.isPresent()) {
            UserSemesterEntity userSemester =
                    userSemesterService.findByIdAndValidatePermissions(
                            userGoogleId, interestDto.getSemesterId());
            UserInterestEntity newInterest =
                    UserInterestEntity.builder()
                            .user(user)
                            .userSemester(userSemester)
                            .subject(subject)
                            .build();
            return userInterestRepository.save(newInterest);
        }
        return recreateIfDeleted(userGoogleId, interest.get(), interestDto);
    }
}
