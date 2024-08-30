package com.borathings.borapagar.user_interest;

import com.borathings.borapagar.user_interest.dto.CreateUserInterestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping("/user/interest")
public interface UserInterestController {

    /**
     * Faz o usuário demonstrar interesse na disciplina escolhida
     *
     * @param interestDto - CreateUserInterestDTO - DTO com as informações do interesse
     * @param subjectId - Long - ID da disciplina
     * @return ResponseEntity<UserInterestEntity> - Entidade do interesse criado
     */
    @PostMapping("/subject/{subjectId}")
    public ResponseEntity<UserInterestEntity> createInterest(
            @RequestBody @Valid CreateUserInterestDTO interestDto, @PathVariable Long subjectId);
}
