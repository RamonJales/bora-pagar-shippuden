package com.borathings.borapagar.course;

import com.borathings.borapagar.course.dto.CourseDTO;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired private CourseRepository courseRepository;

    /**
     * Cria um novo curso
     *
     * @param courseDto - Dados do curso
     * @return Curso criado
     */
    public CourseEntity create(CourseDTO courseDto) {
        return courseRepository.save(courseDto.toEntity());
    }

    /**
     * Retorna todos os cursos
     *
     * @return Lista de cursos
     */
    public List<CourseEntity> findAll() {
        return courseRepository.findAll();
    }

    /**
     * Retorna um curso pelo id. Lança uma exceção caso o curso não seja encontrado
     *
     * @param id - Id do curso
     * @return Curso recuperado
     * @throws EntityNotFoundException - Caso o curso não seja encontrado
     */
    public CourseEntity findByIdOrError(Long id) {
        CourseEntity courseEntity =
                courseRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "Curso com id " + id + " não encontrado"));
        return courseEntity;
    }

    /**
     * Atualiza um curso
     *
     * @param id - Id do curso
     * @param courseDto - Novos dados do curso
     * @return Curso atualizado
     * @throws EntityNotFoundException - Caso o curso não seja encontrado
     */
    public CourseEntity update(Long id, CourseDTO courseDto) {
        findByIdOrError(id);
        CourseEntity courseEntity = courseDto.toEntity();
        courseEntity.setId(id);
        return courseRepository.save(courseEntity);
    }

    /**
     * Deleta um curso. Se o curso não existe, não faz nada
     *
     * @param id - Id do curso
     */
    public void delete(Long id) {
        courseRepository.softDeleteById(id);
    }
}
