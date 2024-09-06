package com.borathings.borapagar.subject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.borathings.borapagar.department.DepartmentEntity;
import com.borathings.borapagar.subject.dto.request.CreateSubjectDTO;
import com.borathings.borapagar.subject.dto.request.UpdateSubjectDTO;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubjectServiceTests {
    @MockBean private SubjectRepository subjectRepository;
    @Autowired private SubjectService subjectService;
    private SubjectEntity subject;

    @BeforeEach
    public void setUp() {
        DepartmentEntity department = DepartmentEntity.builder().id(1L).build();
        subject =
                SubjectEntity.builder()
                        .name("MATEMÁTICA ELEMENTAR")
                        .code("IMD0001")
                        .id(1L)
                        .department(department)
                        .build();

        when(subjectRepository.findAll()).thenReturn(List.of(subject));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(subjectRepository.findById(2L))
                .thenThrow(new EntityNotFoundException("Disciplina não encontrada"));
        when(subjectRepository.save(any())).thenAnswer((invocation) -> invocation.getArgument(0));
        doNothing().when(subjectRepository).deleteById(1L);
    }

    @Test
    public void shouldCreateSubject() {
        CreateSubjectDTO subjectDto =
                CreateSubjectDTO.builder().name("MATEMÁTICA ELEMENTAR").build();
        SubjectEntity createdSubject = subjectService.create(subjectDto);
        assertEquals(subject.getName(), createdSubject.getName());
    }

    @Test
    public void shouldGetAllSubjects() {
        List<SubjectEntity> subjects = subjectService.findAll();
        assertEquals(1, subjects.size());
        assertEquals(subject, subjects.get(0));
    }

    @Test
    void shouldGetSubjectById() {
        SubjectEntity subject = subjectService.findByIdOrError(1L);
        assertEquals(this.subject, subject);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenRequestNonExistentSubject() {
        assertThrows(EntityNotFoundException.class, () -> subjectService.findByIdOrError(2L));
    }

    @Test
    void shouldUpdateSubject() {
        SubjectEntity subjectCopy = subject;
        subjectCopy.setId(null);
        UpdateSubjectDTO subjectDTO =
                UpdateSubjectDTO.builder().name("MATEMÁTICA ELEMENTAR").build();
        SubjectEntity updatedSubject = subjectService.update(1L, subjectDTO);

        assertEquals(1L, updatedSubject.getId());
        assertEquals(subjectCopy.getName(), updatedSubject.getName());
    }

    @Test
    void shouldDeleteSubject() {
        assertDoesNotThrow(() -> subjectService.delete(1L));
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdatingNonExistentSubject() {
        SubjectEntity subjectCopy = subject;
        subjectCopy.setId(null);
        UpdateSubjectDTO subjectDTO =
                UpdateSubjectDTO.builder().name("MATEMÁTICA ELEMENTAR").build();
        assertThrows(EntityNotFoundException.class, () -> subjectService.update(2L, subjectDTO));
    }
}
