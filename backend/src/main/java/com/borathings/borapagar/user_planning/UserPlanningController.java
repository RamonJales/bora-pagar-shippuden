package com.borathings.borapagar.user_planning;

import com.borathings.borapagar.user_planning.dto.CreateUserPlanningDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping("/user/planning")
public interface UserPlanningController {

    /**
     * Faz o usuário adicionar a disciplina no seu planejamento
     *
     * @param planningDto - CreateUserPlanningDTO - DTO com as informações do elemento do
     *     planejamento
     * @param subjectId - Long - ID da disciplina
     * @return ResponseEntity<UserPlanningEntity> - Elemento do planejamento criado
     */
    @PostMapping("/subject/{subjectId}")
    public ResponseEntity<UserPlanningEntity> createPlanning(
            @RequestBody @Valid CreateUserPlanningDTO planningDto, @PathVariable Long subjectId);
}
