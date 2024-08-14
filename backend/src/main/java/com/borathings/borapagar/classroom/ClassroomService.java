package com.borathings.borapagar.classroom;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassroomService {
    @Autowired ClassroomRepository classroomRepository;

    /**
     * Busca uma turma pelo ID. Lança <code>EntityNotFoundException</code> caso a turma não seja
     * encontrada.
     *
     * @param classroomId - Id da turma a ser buscada
     * @return Dados da turma
     */
    public ClassroomEntity findByIdOrError(Long classroomId) {
        return classroomRepository
                .findById(classroomId)
                .orElseThrow(() -> new EntityNotFoundException("Turma não encontrada"));
    }
}
