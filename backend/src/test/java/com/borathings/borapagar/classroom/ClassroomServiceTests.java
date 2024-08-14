package com.borathings.borapagar.classroom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ClassroomServiceTests {
    @MockBean ClassroomRepository classroomRepository;
    @Autowired ClassroomService classroomService;

    @Test
    public void shouldFindClassroomById() {
        ClassroomEntity classroomEntity =
                ClassroomEntity.builder().id(1L).yearPeriod("2024.2").seats(30).build();

        when(classroomRepository.findById(1L)).thenReturn(Optional.of(classroomEntity));

        ClassroomEntity foundClassroom = classroomService.findByIdOrError(1L);
        assertEquals(classroomEntity, foundClassroom);
    }

    @Test
    public void shouldThrowEntityNotFoundIfClassroomDoesNotExist() {
        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> classroomService.findByIdOrError(1L));
    }
}
