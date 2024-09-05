package com.borathings.borapagar.user_planning;

import com.borathings.borapagar.user_planning.dto.CreateUserPlanningDTO;
import com.borathings.borapagar.user_planning.dto.UpdateUserPlanningDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    /**
     * Retorna todos os elementos do planejamento do usuário autenticado
     *
     * @return ResponseEntity<List<UserPlanning>> - Elementos do planejamento do usuário
     */
    @GetMapping
    public ResponseEntity<List<UserPlanningEntity>> findPlanningFromAuthenticatedUser();

    /**
     * Retorna informações sobre um interesse específico do usuário
     *
     * @param subjectId - Long - Id da disciplina que o usuário manifestou interesse
     * @return ResponseEntity<UserPlanningEntity> - Entidade com informações sobre o interesse do
     *     usuário nesta disciplina
     */
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<UserPlanningEntity> findSpecificElement(@PathVariable Long subjectId);

    /**
     * Atualiza o semestre que o usuário autenticado planeja pagar uma disciplina
     *
     * @param planningDto - UpdateUserPlanningDTO - DTO contendo o id do novo semestre
     * @param subjectId - Long - Id da disciplina a ser atualizada
     * @return ResponseEntity<UserPlanningEntity> - Elemento do planejamento com o semestre
     *     atualizado
     */
    @PutMapping("/subject/{subjectId}")
    public ResponseEntity<UserPlanningEntity> updatePlanningSemester(
            @RequestBody @Valid UpdateUserPlanningDTO planningDto, @PathVariable Long subjectId);

    /**
     * Remove uma disciplina do planejamento do usuário
     *
     * @param subjectId - Id da disciplina a ser removida
     * @return ResponseEntity<Void> - Resposta vazia
     */
    @DeleteMapping("/subject/{subjectId}")
    public ResponseEntity<Void> deletePlanningElement(@PathVariable Long subjectId);

    /**
     * Alterna o status de uma disciplina no planejamento do usuário autenticado entre concluída e
     * não concluída.
     *
     * @param subjectId - Long - Id da disciplina
     * @return ResponseEntity<String> - Mensagem indicando o novo status da disciplina
     */
    @PostMapping("/subject/{subjectId}/toggle-completed")
    public ResponseEntity<String> toggleCompleted(@PathVariable Long subjectId);
}
