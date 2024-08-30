package com.borathings.borapagar.user_interest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping("/user/interest")
public interface UserInterestController {

    /**
     * Retorna informações sobre um interesse específico do usuário
     *
     * @param subjectId - Long - Id da disciplina que o usuário manifestou interesse
     * @return ResponseEntity<UserInterestEntity> - Entidade com informações sobre o interesse do
     *     usuário nesta disciplina
     */
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<UserInterestEntity> getSpecificInterest(@PathVariable Long subjectId);
}
