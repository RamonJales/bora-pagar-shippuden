package com.borathings.borapagar.user;

import com.borathings.borapagar.user.dto.response.DefaultUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/** UserController */
@RequestMapping("/users")
public interface UserController {

    @GetMapping("/me")
    public ResponseEntity<DefaultUserDTO> getCurrentUser(Authentication authentication);
}
