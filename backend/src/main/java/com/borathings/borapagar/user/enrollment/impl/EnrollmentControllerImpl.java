package com.borathings.borapagar.user.enrollment.impl;

import com.borathings.borapagar.user.enrollment.EnrollmentController;
import com.borathings.borapagar.user.enrollment.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnrollmentControllerImpl implements EnrollmentController {
    @Autowired EnrollmentService enrollmentService;

    @Override
    public ResponseEntity<String> enrollInClassroom(Long classroomId) {
        String authenticatedGoogleId =
                SecurityContextHolder.getContext().getAuthentication().getName();
        enrollmentService.enrollUserInClassroom(authenticatedGoogleId, classroomId);
        return ResponseEntity.ok().body("Usuário matriculado com sucesso");
    }
}
