package com.borathings.borapagar.user_planning.impl;

import com.borathings.borapagar.user_planning.UserPlanningController;
import com.borathings.borapagar.user_planning.UserPlanningEntity;
import com.borathings.borapagar.user_planning.UserPlanningService;
import com.borathings.borapagar.user_planning.dto.CreateUserPlanningDTO;
import com.borathings.borapagar.user_planning.dto.UpdateUserPlanningDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserPlanningControllerImpl implements UserPlanningController {
    @Autowired UserPlanningService userPlanningService;

    @Override
    public ResponseEntity<UserPlanningEntity> createPlanning(
            @Valid CreateUserPlanningDTO interestDto, Long subjectId) {
        String authenticatedUserGoogleId =
                SecurityContextHolder.getContext().getAuthentication().getName();
        UserPlanningEntity interest =
                userPlanningService.create(authenticatedUserGoogleId, subjectId, interestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(interest);
    }

    @Override
    public ResponseEntity<List<UserPlanningEntity>> findPlanningFromAuthenticatedUser() {
        String authenticatedGoogleId =
                SecurityContextHolder.getContext().getAuthentication().getName();
        List<UserPlanningEntity> interests =
                userPlanningService.findPlanningByUser(authenticatedGoogleId);
        return ResponseEntity.ok(interests);
    }

    @Override
    public ResponseEntity<UserPlanningEntity> findSpecificElement(Long subjectId) {
        String authUserGoogleId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserPlanningEntity planning =
                userPlanningService.getPlanningElement(authUserGoogleId, subjectId);
        return ResponseEntity.ok(planning);
    }

    @Override
    public ResponseEntity<UserPlanningEntity> updatePlanningSemester(
            @Valid UpdateUserPlanningDTO planningDto, Long subjectId) {
        String authUserGoogleId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserPlanningEntity updatedPlanning =
                userPlanningService.updatePlanningSemester(
                        authUserGoogleId, subjectId, planningDto);
        return ResponseEntity.ok(updatedPlanning);
    }
}
