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
    public CourseEntity createCourse(CourseEntity courseEntity) {
        return courseRepository.save(courseEntity);
    }


    /**
     * Retorna todos os cursos
     *
     * @return Lista de cursos
     */

    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }


    /**
     * Retorna um curso pelo id
     *
     * @param id - Id do curso
     * @return Curso recuperado
     * @throws EntityNotFoundException - Caso o curso não seja encontrado
     */
    public CourseEntity getCourseById(Long id) {
        CourseEntity courseEntity = courseRepository.findById(id).orElse(null);
        if (courseEntity == null) {
            throw new EntityNotFoundException("Course of id " + id + " not found");
        }
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
    public CourseEntity updateCourse(Long id, CourseEntity courseEntity) {
        getCourseById(id);
        courseEntity.setId(id);
        return courseRepository.save(courseEntity);
    }


    /**
     * Deleta um curso. Se o curso não existe, não faz nada
     *
     * @param id - Id do curso
     */
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
