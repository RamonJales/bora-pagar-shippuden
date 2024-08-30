package com.borathings.borapagar.user_interest.impl;

import com.borathings.borapagar.user_interest.UserInterestController;
import com.borathings.borapagar.user_interest.UserInterestEntity;
import com.borathings.borapagar.user_interest.UserInterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInterestControllerImpl implements UserInterestController {
    @Autowired private UserInterestService userInterestService;

    @Override
    public ResponseEntity<UserInterestEntity> getSpecificInterest(Long subjectId) {
        String authUserGoogleId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInterestEntity interest =
                userInterestService.getSpecificInterest(authUserGoogleId, subjectId);
        return ResponseEntity.ok(interest);
    }
}
