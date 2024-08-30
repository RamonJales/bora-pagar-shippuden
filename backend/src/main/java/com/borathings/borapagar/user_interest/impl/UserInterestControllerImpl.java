package com.borathings.borapagar.user_interest.impl;

import com.borathings.borapagar.user_interest.UserInterestController;
import com.borathings.borapagar.user_interest.UserInterestEntity;
import com.borathings.borapagar.user_interest.UserInterestService;
import com.borathings.borapagar.user_interest.dto.CreateUserInterestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInterestControllerImpl implements UserInterestController {
    @Autowired UserInterestService userInterestService;

    @Override
    public ResponseEntity<UserInterestEntity> createInterest(
            @Valid CreateUserInterestDTO interestDto, Long subjectId) {
        String authenticatedUserGoogleId =
                SecurityContextHolder.getContext().getAuthentication().getName();
        UserInterestEntity interest =
                userInterestService.create(authenticatedUserGoogleId, subjectId, interestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(interest);
    }
}
