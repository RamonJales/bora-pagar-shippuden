package com.borathings.borapagar.user_semester.impl;

import com.borathings.borapagar.user_semester.UserSemesterController;
import com.borathings.borapagar.user_semester.UserSemesterEntity;
import com.borathings.borapagar.user_semester.UserSemesterService;
import com.borathings.borapagar.user_semester.dto.UserSemesterDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserSemesterControllerImpl implements UserSemesterController {
    @Autowired private UserSemesterService userSemesterService;

    private String getAuthenticatedGoogleId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public ResponseEntity<UserSemesterEntity> create(UserSemesterDTO userSemesterDto) {
        UserSemesterEntity userSemesterEntity =
                userSemesterService.create(getAuthenticatedGoogleId(), userSemesterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSemesterEntity);
    }

    @Override
    public ResponseEntity<List<UserSemesterEntity>> findByAuthenticatedUser() {
        List<UserSemesterEntity> userSemesters =
                userSemesterService.findByAuthenticatedUser(getAuthenticatedGoogleId());
        return ResponseEntity.ok(userSemesters);
    }

    @Override
    public ResponseEntity<UserSemesterEntity> findById(Long id) {
        UserSemesterEntity userSemester = userSemesterService.findByIdOrError(id);
        return ResponseEntity.ok(userSemester);
    }

    @Override
    public ResponseEntity<UserSemesterEntity> update(
            Long id, @Valid UserSemesterDTO userSemesterDto) {
        UserSemesterEntity userSemesterEntity =
                userSemesterService.update(id, getAuthenticatedGoogleId(), userSemesterDto);
        return ResponseEntity.ok(userSemesterEntity);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        userSemesterService.delete(id, getAuthenticatedGoogleId());
        return ResponseEntity.ok("Semestre deletado com sucesso.");
    }
}
