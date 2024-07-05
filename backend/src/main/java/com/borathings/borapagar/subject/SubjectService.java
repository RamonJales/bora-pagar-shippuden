package com.borathings.borapagar.subject;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    @Autowired private SubjectRepository subjectRepository;

    /**
     * Salva uma nova disciplina no banco de dados
     *
     * @param subjectDto - Dados da disciplina
     * @return Disciplina salva
     */
    public SubjectEntity create(SubjectEntity subjectEntity) {
        return subjectRepository.save(subjectEntity);
    }

    /**
     * Retorna todas as disciplinas cadastradas
     *
     * @return Lista de disciplinas
     */
    public List<SubjectEntity> findAll() {
        return subjectRepository.findAll();
    }

    /**
     * Busca uma disciplina pelo id.
     *
     * @throws EntityNotFoundException se a disciplina não existir
     * @param id
     * @return Disciplina encontrada
     */
    public SubjectEntity findById(Long id) {
        SubjectEntity subject =
                subjectRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "Subject with id + " + id + " not found"));
        return subject;
    }

    /**
     * Atualiza os dados de uma disciplina
     *
     * @param id - Id da disciplina
     * @param subjectDto - Novos dados da disciplina
     * @throws EntityNotFoundException se a disciplina não existir
     * @return Disciplina atualizada
     */
    public SubjectEntity update(Long id, SubjectEntity subjectEntity) {
        findById(id);
        subjectEntity.setId(id);
        return subjectRepository.save(subjectEntity);
    }

    /**
     * Deleta uma disciplina pelo id
     *
     * @param id - Id da disciplina
     */
    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }
}
