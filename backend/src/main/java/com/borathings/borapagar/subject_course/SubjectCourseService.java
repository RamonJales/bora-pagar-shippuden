package com.borathings.borapagar.subject_course;

import com.borathings.borapagar.course.CourseService;
import com.borathings.borapagar.subject.SubjectService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * Serviço para manipulação de disciplinas na grade de um curso.
 *
 * <p>Este serviço assume que o curso do respectivo courseId já existe no banco de dados pois essa
 * checagem deve ser feita por um middleware antes de chamar os métodos desse serviço.
 */
@Service
public class SubjectCourseService {
    @Autowired private SubjectCourseRepository subjectCourseRepository;

    @Autowired private CourseService courseService;
    @Autowired private SubjectService subjectService;

    /**
     * Adiciona uma nova disciplina na grade do curso. Lança <code>DuplicateKeyException</code> se a
     * disciplina já foi cadastrada na grade.
     *
     * @param courseId - Id do curso
     * @param subjectCourseEntity - Dados da disciplina
     * @throws DuplicateKeyException - Caso a disciplina já tenha sido cadastrada no curso
     * @return Disciplina adicionada
     */
    public SubjectCourseEntity addSubjectToCourseSchedule(
            Long courseId, SubjectCourseEntity subjectCourseEntity) {
        Long subjectId = subjectCourseEntity.getKeyId().getSubjectId();
        SubjectCourseKey subjectCourseKey =
                SubjectCourseKey.builder().subjectId(subjectId).courseId(courseId).build();
        subjectCourseEntity.setKeyId(subjectCourseKey);
        subjectCourseEntity.setCourse(courseService.findByIdOrError(courseId));
        subjectCourseEntity.setSubject(subjectService.findByIdOrError(subjectId));
        subjectCourseRepository
                .findByCourseIdAndSubjectId(courseId, subjectId)
                .ifPresent(
                        (value) -> {
                            throw new DuplicateKeyException(
                                    String.format(
                                            "Disciplina de id %d já cadastrada na grade do curso de"
                                                    + " id %d",
                                            subjectId, courseId));
                        });

        return subjectCourseRepository.save(subjectCourseEntity);
    }

    /**
     * Busca informações de uma disciplina em relação a grade do curso. Lança <code>EntityNotFound
     * </code> se a disciplina não foi encontrada na grade.
     *
     * @param courseId - Id do curso
     * @param subjectId - Id da disciplina
     * @throws EntityNotFoundException - Caso a disciplina não seja encontrada na grade do curso
     * @return Informações da disciplina na grade do curso (ex: nível, se é optativa ou obrigatória)
     */
    public SubjectCourseEntity getSubjectInfoFromCourseScheduleOrError(
            Long courseId, Long subjectId) {
        SubjectCourseEntity subjectCourse =
                subjectCourseRepository
                        .findByCourseIdAndSubjectId(courseId, subjectId)
                        .orElseThrow(
                                () -> {
                                    throw new EntityNotFoundException(
                                            String.format(
                                                    "Disciplina de id %d não encontrada na grade do"
                                                            + " curso de id %d",
                                                    subjectId, courseId));
                                });
        return subjectCourse;
    }

    /**
     * Lista todas as disciplinas da grade de um curso.
     *
     * @param courseId - Id do curso
     * @return Lista de disciplinas
     */
    public List<SubjectCourseEntity> getAllSubjectsFromCourseSchedule(Long courseId) {
        return subjectCourseRepository.findByCourseId(courseId);
    }

    /**
     * Atualiza informações de uma disciplina em relação a grade do curso.
     *
     * @param courseId - Id do curso
     * @param subjectId - Id da disciplina
     * @param subjectCourseEntity - Novas informações da disciplina na grade
     * @throws EntityNotFoundException - Caso a disciplina não seja encontrada na grade do curso
     * @return Informações atualizadas
     */
    public SubjectCourseEntity updateSubjectInfoFromCourseSchedule(
            Long courseId, Long subjectId, SubjectCourseEntity subjectCourseEntity) {
        SubjectCourseEntity databaseEntity =
                getSubjectInfoFromCourseScheduleOrError(courseId, subjectId);

        BeanUtils.copyProperties(subjectCourseEntity, databaseEntity, "keyId", "course", "subject");
        return subjectCourseRepository.save(databaseEntity);
    }

    /**
     * Remove uma disciplina da grade do curso. Se o registro não existe, não faça nada
     *
     * @param courseId - Id do curso
     * @param subjectId - Id da disciplina
     */
    public void deleteSubjectFromCourseSchedule(Long courseId, Long subjectId) {
        subjectCourseRepository.deleteByCourseIdAndSubjectId(courseId, subjectId);
    }
}
