package com.borathings.borapagar.subject.impl;

import com.borathings.borapagar.subject.SubjectController;
import com.borathings.borapagar.subject.SubjectMapper;
import com.borathings.borapagar.subject.SubjectService;
import com.borathings.borapagar.subject.dto.request.CreateSubjectDTO;
import com.borathings.borapagar.subject.dto.request.UpdateSubjectDTO;
import com.borathings.borapagar.subject.dto.response.DefaultSubjectDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubjectControllerImpl implements SubjectController {
    @Autowired private SubjectMapper subjectMapper;
    @Autowired private SubjectService subjectService;

    @Override
    public ResponseEntity<DefaultSubjectDTO> createSubject(CreateSubjectDTO subjectDto) {
        DefaultSubjectDTO data =
                subjectMapper.subjectToDefaultSubjectDTO(subjectService.create(subjectDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @Override
    public ResponseEntity<List<DefaultSubjectDTO>> getAllSubjects() {
        List<DefaultSubjectDTO> data =
                subjectService.findAll().stream()
                        .map(subjectMapper::subjectToDefaultSubjectDTO)
                        .toList();
        return ResponseEntity.ok(data);
    }

    @Override
    public ResponseEntity<DefaultSubjectDTO> getSubjectById(Long id) {
        DefaultSubjectDTO data =
                subjectMapper.subjectToDefaultSubjectDTO(subjectService.findByIdOrError(id));
        return ResponseEntity.ok(data);
    }

    @Override
    public ResponseEntity<DefaultSubjectDTO> updateSubject(Long id, UpdateSubjectDTO subjectDto) {
        DefaultSubjectDTO data =
                subjectMapper.subjectToDefaultSubjectDTO(subjectService.update(id, subjectDto));
        return ResponseEntity.ok(data);
    }

    @Override
    public ResponseEntity<String> deleteSubject(Long id) {
        subjectService.delete(id);
        return ResponseEntity.ok("Disciplina excluída com sucesso");
    }
}
