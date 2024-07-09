package com.borathings.borapagar.course;

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
     * @param courseEntity - Entidade do curso
     * @return Curso criado
     */
    public CourseEntity create(CourseEntity courseEntity) {
        return courseRepository.save(courseEntity);
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
     * @param courseEntity - Entidade atualizada do curso
     * @return Curso atualizado
     * @throws EntityNotFoundException - Caso o curso não seja encontrado
     */
    public CourseEntity update(Long id, CourseEntity courseEntity) {
        findByIdOrError(id);
        courseEntity.setId(id);
        return courseRepository.save(courseEntity);
    }

    /**
     * Deleta um curso. Se o curso não existe, não faz nada
     *
     * @param id - Id do curso
     */
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}
