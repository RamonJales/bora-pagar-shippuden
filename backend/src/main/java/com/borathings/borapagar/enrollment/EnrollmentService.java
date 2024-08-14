package com.borathings.borapagar.enrollment;

import com.borathings.borapagar.classroom.ClassroomEntity;
import com.borathings.borapagar.classroom.ClassroomService;
import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user.UserRepository;
import com.borathings.borapagar.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
    @Autowired UserRepository userRepository;
    @Autowired UserService userService;
    @Autowired ClassroomService classroomService;

    /**
     * Realiza a matrícula do usuário na turma. Caso o usuário já esteja matriculado, uma exceção do
     * tipo <code>DuplicateKeyException</code> é lançada.
     *
     * @param googleId - Google ID do usuário
     * @param classroomId - ID da turma
     */
    public void enrollUserInClassroom(String googleId, Long classroomId) {
        UserEntity user = userService.findByGoogleIdOrError(googleId);
        ClassroomEntity classroom = classroomService.findByIdOrError(classroomId);
        if (user.getEnrolledClassrooms().contains(classroom)) {
            throw new DuplicateKeyException("Usuário já está matriculado nesta disciplina");
        }
        ;
        user.getEnrolledClassrooms().add(classroom);
        userRepository.save(user);
    }
}
