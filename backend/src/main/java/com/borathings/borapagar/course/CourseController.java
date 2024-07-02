package com.borathings.borapagar.course;

import com.borathings.borapagar.course.dto.CourseDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping("/course")
public interface CourseController {

    /**
     * Insere um novo curso no banco de dados
     *
     * @param courseEntityDto - CourseDTO - Dados do curso
     * @return ResponseEntity<CourseEntity> - Curso criado
     */
    @PostMapping()
    public ResponseEntity<CourseEntity> createCourse(@Valid @RequestBody CourseDTO courseEntityDto);

    /**
     * Retorna todos os cursos cadastrados
     *
     * @return ResponseEntity<List<CourseEntity>> - Lista de cursos
     */
    @GetMapping()
    public ResponseEntity<List<CourseEntity>> findAllCourses();

    /**
     * Retorna um curso específico
     *
     * @param id - Long - ID do curso
     * @return ResponseEntity<CourseEntity> - Curso específico
     */
    @GetMapping("/{id}")
    public ResponseEntity<CourseEntity> findCourseById(@RequestParam Long id);

    /**
     * Atualiza um curso específico
     *
     * @param courseEntityDto - CourseDTO - Dados do curso
     * @param id - Long - ID do curso
     * @return ResponseEntity<CourseEntity> - Curso atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<CourseEntity> updateCourse(
            @RequestBody @Valid CourseDTO courseEntityDto, @PathVariable Long id);

    /**
     * Deleta um curso específico. Se o curso não existir, não faz nada. Independente do caso,
     * retorna uma mensagem de sucesso.
     *
     * @param id - Long - ID do curso
     * @return ResponseEntity<String> - Mensagem de sucesso
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id);
}
