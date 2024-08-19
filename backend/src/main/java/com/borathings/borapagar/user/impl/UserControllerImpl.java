package com.borathings.borapagar.user.impl;

import com.borathings.borapagar.user.UserController;
import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserMapper;
import com.borathings.borapagar.user.UserService;
import com.borathings.borapagar.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.RestController;

/** UserControllerImpl */
@RestController
public class UserControllerImpl implements UserController {

    @Autowired UserService userService;

    @Override
    public ResponseEntity<UserDTO> getCurrentUser(OidcUser currentUser) {
        String currentUserGoogleId = currentUser.getName();
        UserEntity loggedUser = userService.findByGoogleIdOrError(currentUserGoogleId);
        return ResponseEntity.ok(UserMapper.toDTO(loggedUser));
    }
}
