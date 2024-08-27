package com.borathings.borapagar.user_semester;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.borathings.borapagar.user.UserEntity;
import com.borathings.borapagar.user_semester.dto.UserSemesterDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserSemesterController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebMvc
public class UserSemesterControllerTests {
    @Autowired MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean UserSemesterService userSemesterService;

    @Test
    public void shouldCreateSemester() throws Exception {
        when(userSemesterService.create(eq("123"), any()))
                .thenAnswer(
                        (invocation) -> {
                            UserSemesterDTO userSemesterDTO = invocation.getArgument(1);
                            UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
                            return UserSemesterEntity.builder()
                                    .year(userSemesterDTO.getYear())
                                    .period(userSemesterDTO.getPeriod())
                                    .user(user)
                                    .build();
                        });

        UserSemesterDTO userSemesterDTO = UserSemesterDTO.builder().year(2024).period(1).build();

        mockMvc.perform(
                        post("/api/user/semester")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userSemesterDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.year").value(userSemesterDTO.getYear()))
                .andExpect(jsonPath("$.period").value(userSemesterDTO.getPeriod()));
    }

    @Test
    public void createShouldValidateFields() throws Exception {

        UserSemesterDTO userSemesterDTO = UserSemesterDTO.builder().year(-1).period(-1).build();

        mockMvc.perform(
                        post("/api/user/semester")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userSemesterDTO)))
                .andExpect(jsonPath("$.fieldErrors.year").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors.period").isNotEmpty());

        userSemesterDTO = UserSemesterDTO.builder().build();

        mockMvc.perform(
                        post("/api/user/semester")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userSemesterDTO)))
                .andExpect(jsonPath("$.fieldErrors.year").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors.period").isNotEmpty());
    }

    @Test
    public void createShouldNotAllowDuplicates() throws Exception {
        when(userSemesterService.create(eq("123"), any()))
                .thenThrow(new DuplicateKeyException("Semestre já cadastrado"));
        UserSemesterDTO userSemesterDTO = UserSemesterDTO.builder().year(2024).period(1).build();
        mockMvc.perform(
                        post("/api/user/semester")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userSemesterDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldListAllByAuthenticatedUser() throws Exception {
        UserSemesterEntity semester1 = UserSemesterEntity.builder().year(2024).period(1).build();
        UserSemesterEntity semester2 = UserSemesterEntity.builder().year(2024).period(2).build();
        when(userSemesterService.findByAuthenticatedUser("123"))
                .thenReturn(List.of(semester1, semester2));

        mockMvc.perform(get("/api/user/semester").with(jwt().jwt(jwt -> jwt.subject("123"))))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].year").value(semester1.getYear()))
                .andExpect(jsonPath("$[0].period").value(semester1.getPeriod()))
                .andExpect(jsonPath("$[1].year").value(semester2.getYear()))
                .andExpect(jsonPath("$[1].period").value(semester2.getPeriod()));
    }

    @Test
    public void shouldFindById() throws Exception {
        UserSemesterEntity semester = UserSemesterEntity.builder().year(2024).period(1).build();
        when(userSemesterService.findByIdAndValidatePermissions("123", 1L)).thenReturn(semester);

        mockMvc.perform(get("/api/user/semester/1").with(jwt().jwt(jwt -> jwt.subject("123"))))
                .andExpect(jsonPath("$.year").value(semester.getYear()))
                .andExpect(jsonPath("$.period").value(semester.getPeriod()));
    }

    @Test
    public void findByIdShouldValidatePermissionsAndCheckExistence() throws Exception {
        when(userSemesterService.findByIdAndValidatePermissions("123", 1L))
                .thenThrow(new AccessDeniedException("Você não tem permissão para este semestre"));
        when(userSemesterService.findByIdAndValidatePermissions("123", 2L))
                .thenThrow(new EntityNotFoundException("Semestre não encontrado"));

        mockMvc.perform(get("/api/user/semester/1").with(jwt().jwt(jwt -> jwt.subject("123"))))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/user/semester/2").with(jwt().jwt(jwt -> jwt.subject("123"))))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdate() throws Exception {
        when(userSemesterService.update(eq(1L), eq("123"), any()))
                .thenAnswer(
                        (invocation) -> {
                            UserSemesterDTO userSemesterDTO = invocation.getArgument(2);
                            UserEntity user = UserEntity.builder().id(1L).googleId("123").build();
                            return UserSemesterEntity.builder()
                                    .year(userSemesterDTO.getYear())
                                    .period(userSemesterDTO.getPeriod())
                                    .user(user)
                                    .build();
                        });

        UserSemesterDTO userSemesterDTO = UserSemesterDTO.builder().period(1).year(2024).build();

        mockMvc.perform(
                        put("/api/user/semester/1")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userSemesterDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.year").value(userSemesterDTO.getYear()))
                .andExpect(jsonPath("$.period").value(userSemesterDTO.getPeriod()));
    }

    @Test
    public void updateShouldValidatePermissionsAndCheckExistence() throws Exception {
        when(userSemesterService.update(eq(1L), eq("123"), any()))
                .thenThrow(new AccessDeniedException("Você não tem permissão para este semestre"));
        when(userSemesterService.update(eq(2L), eq("123"), any()))
                .thenThrow(new EntityNotFoundException("Semestre não encontrado"));

        UserSemesterDTO userSemesterDTO = UserSemesterDTO.builder().period(1).year(2024).build();
        mockMvc.perform(
                        put("/api/user/semester/1")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userSemesterDTO)))
                .andExpect(status().isForbidden());

        mockMvc.perform(
                        put("/api/user/semester/2")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userSemesterDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateShouldValidateFields() throws Exception {
        UserSemesterDTO userSemesterDTO = UserSemesterDTO.builder().year(-1).period(-1).build();

        mockMvc.perform(
                        put("/api/user/semester/1")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userSemesterDTO)))
                .andExpect(jsonPath("$.fieldErrors.year").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors.period").isNotEmpty());

        userSemesterDTO = UserSemesterDTO.builder().build();

        mockMvc.perform(
                        put("/api/user/semester/1")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userSemesterDTO)))
                .andExpect(jsonPath("$.fieldErrors.year").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors.period").isNotEmpty());
    }

    @Test
    public void updateShouldNotAllowDuplicates() throws Exception {
        when(userSemesterService.update(eq(1L), eq("123"), any()))
                .thenThrow(new DuplicateKeyException("Semestre já cadastrado"));
        UserSemesterDTO userSemesterDTO = UserSemesterDTO.builder().year(2024).period(1).build();
        mockMvc.perform(
                        put("/api/user/semester/1")
                                .with(jwt().jwt(jwt -> jwt.subject("123")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userSemesterDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldDelete() throws Exception {
        doNothing().when(userSemesterService).delete(1L, "123");
        mockMvc.perform(delete("/api/user/semester/1").with(jwt().jwt(jwt -> jwt.subject("123"))))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteShouldValidatePermissionsAndCheckExistence() throws Exception {
        doThrow(AccessDeniedException.class).when(userSemesterService).delete(1L, "123");
        doThrow(EntityNotFoundException.class).when(userSemesterService).delete(2L, "123");

        mockMvc.perform(delete("/api/user/semester/1").with(jwt().jwt(jwt -> jwt.subject("123"))))
                .andExpect(status().isForbidden());

        mockMvc.perform(delete("/api/user/semester/2").with(jwt().jwt(jwt -> jwt.subject("123"))))
                .andExpect(status().isNotFound());
    }
}
