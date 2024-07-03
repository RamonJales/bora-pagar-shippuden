package com.borathings.borapagar.subject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.borathings.borapagar.subject.dto.SubjectDTO;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    /**
     * Salva uma nova disciplina no banco de dados
     * @param subjectDto - Dados da disciplina
     * @return Disciplina salva
     */
    public SubjectEntity create(SubjectDTO subjectDto) {
        return subjectRepository.save(subjectDto.toEntity());
    }

    /**
     * Retorna todas as disciplinas cadastradas
     * @return Lista de disciplinas
     */
    public List<SubjectEntity> findAll() {
        return subjectRepository.findAll();
    }

    /**
     * Busca uma disciplina pelo id.
     * @throws EntityNotFoundException se a disciplina não existir
     * @param id
     * @return Disciplina encontrada
     */
    public SubjectEntity findById(Long id) {
        SubjectEntity subject = subjectRepository.findById(id).orElse(null);
        if(subject == null) {
            throw new EntityNotFoundException("Subject with id " + id + " not found");
        }
        return subject;
    }

    /**
     * Atualiza os dados de uma disciplina
     * @param id - Id da disciplina
     * @param subjectDto - Novos dados da disciplina
     * @throws EntityNotFoundException se a disciplina não existir
     * @return Disciplina atualizada
     */
    public SubjectEntity update(Long id, SubjectDTO subjectDto) {
        SubjectEntity subject = findById(id);
        subject = subjectDto.toEntity();
        subject.setId(id);
        return subjectRepository.save(subject);
    }

    /**
     * Deleta uma disciplina pelo id
     * @param id - Id da disciplina
     */
    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }
}
