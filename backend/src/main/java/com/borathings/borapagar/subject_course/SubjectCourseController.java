package com.borathings.borapagar.subject_course;

import com.borathings.borapagar.subject_course.dto.CreateSubjectCourseRequest;
import com.borathings.borapagar.subject_course.dto.UpdateSubjectCourseRequest;
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
@RequestMapping("/course/{courseId}/subject")
public interface SubjectCourseController {
    /**
     * Adiciona a matéria na grade do curso
     *
     * @param courseId - ID do curso
     * @param createSubjectCourseRequest - Dados da disciplina
     * @return - Retorna as informações adicionadas
     */
    @PostMapping
    public ResponseEntity<SubjectCourseEntity> addSubjectToCourseSchedule(
            @PathVariable Long courseId,
            @RequestBody @Valid CreateSubjectCourseRequest createSubjectCourseRequest);

    /**
     * Lista todas as disciplinas da grade de um curso
     *
     * @param courseId - Id do curso
     * @return - Retorna a lista de disciplinas
     */
    @GetMapping
    public ResponseEntity<List<SubjectCourseEntity>> getAllSubjectsFromCourseSchedule(
            @PathVariable Long courseId);

    /**
     * Busca informações de uma disciplina em relação a grade do curso
     *
     * @param courseId - ID do curso
     * @param subjectId - ID da disciplina
     * @return Informações da disciplina na grade do curso (ex: nível, tipo de disciplina)
     */
    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectCourseEntity> getSubjectInfoFromCourseSchedule(
            @PathVariable Long courseId, @PathVariable Long subjectId);

    /**
     * Atualiza informações de uma disciplina em relação a grade do curso.
     *
     * @param courseId - ID do curso
     * @param subjectId - ID da disciplina
     * @param updateSubjectCourseRequest - Dados da disciplina na grade
     * @return - Retorna as informações atualizadas
     */
    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectCourseEntity> updateSubjectInfoFromCourseSchedule(
            @PathVariable Long courseId,
            @PathVariable Long subjectId,
            @RequestBody @Valid UpdateSubjectCourseRequest updateSubjectCourseRequest);

    /**
     * Remove a disciplina da grade do curso. Se uma das entidades não existe, não faça nada.
     *
     * @param courseId - ID do curso
     * @param subjectId - ID da disciplina
     * @return - Retorna uma mensagem de sucesso
     */
    @DeleteMapping("/{subjectId}")
    public ResponseEntity<String> removeSubjectFromCourseSchedule(
            @PathVariable Long courseId, @PathVariable Long subjectId);
}
