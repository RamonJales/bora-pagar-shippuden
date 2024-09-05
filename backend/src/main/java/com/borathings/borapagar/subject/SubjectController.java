package com.borathings.borapagar.subject;

import com.borathings.borapagar.subject.dto.request.CreateSubjectDTO;
import com.borathings.borapagar.subject.dto.request.UpdateSubjectDTO;
import com.borathings.borapagar.subject.dto.response.DefaultSubjectDTO;
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
@RequestMapping("/subject")
public interface SubjectController {

    /**
     * Cria uma nova disciplina
     *
     * @param subjectDto - Dados da disciplina
     * @return Disciplina criada
     */
    @PostMapping
    public ResponseEntity<DefaultSubjectDTO> createSubject(
            @RequestBody @Valid CreateSubjectDTO subjectDto);

    /**
     * Retorna todas as disciplinas cadastradas
     *
     * @return Lista de disciplinas
     */
    @GetMapping
    public ResponseEntity<List<DefaultSubjectDTO>> getAllSubjects();

    /**
     * Retorna uma disciplina pelo id
     *
     * @param id - Id da disciplina
     * @return Disciplina recuperada
     */
    @GetMapping("/{id}")
    public ResponseEntity<DefaultSubjectDTO> getSubjectById(@PathVariable Long id);

    /**
     * Atualiza os dados de uma disciplina
     *
     * @param id - Id da disciplina
     * @param subjectDto - Novos dados da disciplina
     * @return Disciplina atualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<DefaultSubjectDTO> updateSubject(
            @PathVariable Long id, @RequestBody @Valid UpdateSubjectDTO subjectDto);

    /**
     * Deleta uma disciplina pelo id. Se a disciplina não existe, não faz nada.
     *
     * @param id - Id da disciplina
     * @return Mensagem de sucesso
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable Long id);
}
