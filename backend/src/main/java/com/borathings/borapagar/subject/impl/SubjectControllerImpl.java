package com.borathings.borapagar.subject.impl;

import com.borathings.borapagar.subject.SubjectController;
import com.borathings.borapagar.subject.SubjectEntity;
import com.borathings.borapagar.subject.SubjectMapper;
import com.borathings.borapagar.subject.SubjectService;
import com.borathings.borapagar.subject.dto.request.CreateSubjectDTO;
import com.borathings.borapagar.subject.dto.request.UpdateSubjectDTO;
import com.borathings.borapagar.subject.dto.response.SubjectResponseDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubjectControllerImpl implements SubjectController {
    @Autowired private SubjectMapper subjectMapper;
    @Autowired private SubjectService subjectService;

    @Override
    public ResponseEntity<SubjectResponseDTO> createSubject(CreateSubjectDTO subjectDto) {
        SubjectResponseDTO data =
                subjectMapper.toSubjectResponseDTO(subjectService.create(subjectDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @Override
    public ResponseEntity<List<SubjectResponseDTO>> getAllSubjects() {
        List<SubjectResponseDTO> data =
                subjectService.findAll().stream().map(subjectMapper::toSubjectResponseDTO).toList();
        return ResponseEntity.ok(data);
    }

    @Override
    public ResponseEntity<SubjectResponseDTO> getSubjectById(Long id) {
        SubjectResponseDTO data =
                subjectMapper.toSubjectResponseDTO(subjectService.findByIdOrError(id));
        return ResponseEntity.ok(data);
    }

    @Override
    public ResponseEntity<SubjectResponseDTO> updateSubject(Long id, UpdateSubjectDTO subjectDto) {
        SubjectResponseDTO data =
                subjectMapper.toSubjectResponseDTO(subjectService.update(id, subjectDto));
        return ResponseEntity.ok(data);
    }

    @Override
    public ResponseEntity<String> deleteSubject(Long id) {
        subjectService.delete(id);
        return ResponseEntity.ok("Disciplina excluída com sucesso");
    }

    @Override
    public ResponseEntity<List<SubjectEntity>> search(@RequestParam(required = false) String name){
        ResponseEntity<List<SubjectEntity>> entity =
                subjectService.findByNameContainingIgnoreCase(name);
        return entity;
    }
}
