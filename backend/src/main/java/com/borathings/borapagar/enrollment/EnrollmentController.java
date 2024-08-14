package com.borathings.borapagar.enrollment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping("/user/enrollment")
public interface EnrollmentController {

    /**
     * Matricula o usuário autenticado em uma turma
     *
     * @param userDetails - UserDetails - Usuário autenticado
     * @param classroomId - Long - ID da turma em que o usuário irá se matricular
     * @return ResponseEntity<String> - Mensagem indicando o sucesso da matrícula
     */
    @PostMapping("/{classroomId}")
    public ResponseEntity<String> enrollInClassroom(@PathVariable Long classroomId);
}
