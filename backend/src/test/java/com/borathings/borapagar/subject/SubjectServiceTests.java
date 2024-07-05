package com.borathings.borapagar.subject;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
        subject = new SubjectEntity();
        subject.setName("MATEMÁTICA ELEMENTAR");
        subject.setCode("IMD0001");
        subject.setId(1L);
        subject.setDeleted(false);

        when(subjectRepository.findAll()).thenReturn(List.of(subject));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(subjectRepository.findById(2L)).thenThrow(EntityNotFoundException.class);
        when(subjectRepository.save(subject)).thenReturn(subject);
        doNothing().when(subjectRepository).deleteById(1L);
    }

    @Test
    public void shouldCreateSubject() {
        SubjectEntity createdSubject = subjectService.create(subject);
        assert createdSubject.equals(subject);
    }

    @Test
    public void shouldGetAllSubjects() {
        List<SubjectEntity> subjects = subjectService.findAll();
        assert subjects.size() == 1;
        assert subjects.get(0).equals(subject);
    }

    @Test
    void shouldGetSubjectById() {
        SubjectEntity subject = subjectService.findById(1L);
        assert subject.equals(this.subject);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenRequestNonExistentSubject() {
        try {
            subjectService.findById(2L);
        } catch (EntityNotFoundException e) {
            assert true;
            return;
        }

        assert false;
    }

    @Test
    void shouldUpdateSubject() {
        SubjectEntity subjectCopy = subject;
        subjectCopy.setId(null);
        SubjectEntity updatedSubject = subjectService.update(1L, subjectCopy);

        assert updatedSubject.getId().equals(1L);
        assert updatedSubject.getName().equals(subjectCopy.getName());
    }

    @Test
    void shouldDeleteSubject() {
        subjectService.delete(1L);
        assert true;
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdatingNonExistentSubject() {
        try {
            SubjectEntity subjectCopy = subject;
            subjectCopy.setId(null);
            subjectService.update(2L, subjectCopy);
        } catch (EntityNotFoundException e) {
            assert true;
            return;
        }

        assert false;
    }
}
