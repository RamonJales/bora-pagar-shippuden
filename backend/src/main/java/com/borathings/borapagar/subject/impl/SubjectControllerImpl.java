package com.borathings.borapagar.subject.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.borathings.borapagar.subject.SubjectController;
import com.borathings.borapagar.subject.SubjectEntity;
import com.borathings.borapagar.subject.SubjectService;
import com.borathings.borapagar.subject.dto.SubjectDTO;

@RestController
public class SubjectControllerImpl implements SubjectController{
    
    @Autowired
    private SubjectService subjectService;

    @Override
    public ResponseEntity<SubjectEntity> createSubject(SubjectDTO subjectDto) {
       return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.create(subjectDto.toEntity()));
    }

    @Override
    public ResponseEntity<List<SubjectEntity>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.findAll());
    }

    @Override
    public ResponseEntity<SubjectEntity> getSubjectById(Long id) {
        return ResponseEntity.ok(subjectService.findById(id));
    }

    @Override
    public ResponseEntity<SubjectEntity> updateSubject(Long id, SubjectDTO subjectDto) {
        return ResponseEntity.ok(subjectService.update(id, subjectDto.toEntity()));
    }

    @Override
    public ResponseEntity<String> deleteSubject(Long id) {
        subjectService.delete(id);
        return ResponseEntity.ok("Subject deleted successfully");
    }

    
}
