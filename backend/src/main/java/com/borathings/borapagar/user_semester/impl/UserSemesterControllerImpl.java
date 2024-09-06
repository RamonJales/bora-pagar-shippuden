package com.borathings.borapagar.user_semester.impl;

import com.borathings.borapagar.user_semester.UserSemesterController;
import com.borathings.borapagar.user_semester.UserSemesterEntity;
import com.borathings.borapagar.user_semester.UserSemesterMapper;
import com.borathings.borapagar.user_semester.UserSemesterService;
import com.borathings.borapagar.user_semester.dto.request.CreateUserSemesterDTO;
import com.borathings.borapagar.user_semester.dto.request.UpdateUserSemesterDTO;
import com.borathings.borapagar.user_semester.dto.response.UserSemesterResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserSemesterControllerImpl implements UserSemesterController {
    @Autowired private UserSemesterMapper userSemesterMapper;
    @Autowired private UserSemesterService userSemesterService;

    private String getAuthenticatedGoogleId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public ResponseEntity<UserSemesterResponseDTO> create(CreateUserSemesterDTO userSemesterDto) {
        UserSemesterEntity userSemesterEntity =
                userSemesterService.create(getAuthenticatedGoogleId(), userSemesterDto);
        UserSemesterResponseDTO userSemesterDtoResponse =
                userSemesterMapper.toUserSemesterResponseDTO(userSemesterEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSemesterDtoResponse);
    }

    @Override
    public ResponseEntity<List<UserSemesterResponseDTO>> findByAuthenticatedUser() {
        List<UserSemesterEntity> userSemesters =
                userSemesterService.findByAuthenticatedUser(getAuthenticatedGoogleId());
        List<UserSemesterResponseDTO> userSemestersDto =
                userSemesters.stream().map(userSemesterMapper::toUserSemesterResponseDTO).toList();
        return ResponseEntity.ok(userSemestersDto);
    }

    @Override
    public ResponseEntity<UserSemesterResponseDTO> findById(Long id) {
        UserSemesterEntity userSemester =
                userSemesterService.findByIdAndValidatePermissions(getAuthenticatedGoogleId(), id);
        UserSemesterResponseDTO userSemesterDtoResponse =
                userSemesterMapper.toUserSemesterResponseDTO(userSemester);
        return ResponseEntity.ok(userSemesterDtoResponse);
    }

    @Override
    public ResponseEntity<UserSemesterResponseDTO> update(
            Long id, @Valid UpdateUserSemesterDTO userSemesterDto) {
        UserSemesterEntity userSemesterEntity =
                userSemesterService.update(id, getAuthenticatedGoogleId(), userSemesterDto);
        UserSemesterResponseDTO userSemesterDtoResponse =
                userSemesterMapper.toUserSemesterResponseDTO(userSemesterEntity);
        return ResponseEntity.ok(userSemesterDtoResponse);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        userSemesterService.delete(id, getAuthenticatedGoogleId());
        return ResponseEntity.ok("Semestre deletado com sucesso.");
    }
}
