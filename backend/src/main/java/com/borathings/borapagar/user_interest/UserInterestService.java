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

    public void delete(String userGoogleId, Long subjectId) {
        UserInterestEntity interest =
                findByUserGoogleIdAndSubjectIdOrError(userGoogleId, subjectId);
        userSubjectInterestRepository.softDeleteById(interest.getId());
    }
}
