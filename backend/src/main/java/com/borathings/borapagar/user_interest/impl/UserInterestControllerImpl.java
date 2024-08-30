package com.borathings.borapagar.user_interest.impl;

import com.borathings.borapagar.user_interest.UserInterestController;
import com.borathings.borapagar.user_interest.UserInterestEntity;
import com.borathings.borapagar.user_interest.UserInterestService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInterestControllerImpl implements UserInterestController {
    @Autowired private UserInterestService userInterestService;

    @Override
    public ResponseEntity<List<UserInterestEntity>> findInterestsFromAuthenticatedUser() {
        String authenticatedGoogleId =
                SecurityContextHolder.getContext().getAuthentication().getName();
        List<UserInterestEntity> interests =
                userInterestService.findInterestsByUser(authenticatedGoogleId);
        return ResponseEntity.ok(interests);
    }
}
