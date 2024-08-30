package com.borathings.borapagar.user_interest;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping("/user/interest")
public interface UserInterestController {
    /**
     * Encontra todos os interesses do usuário autenticado.
     *
     * @return ResponseEntity<List<UserInterestEntity>> - Lista de interesses do usuário
     *     autenticado.
     */
    @GetMapping
    public ResponseEntity<List<UserInterestEntity>> findInterestsFromAuthenticatedUser();
}
