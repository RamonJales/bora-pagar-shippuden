package com.borathings.borapagar.user_semester;

import com.borathings.borapagar.user_semester.dto.request.CreateUserSemesterDTO;
import com.borathings.borapagar.user_semester.dto.request.UpdateUserSemesterDTO;
import com.borathings.borapagar.user_semester.dto.response.UserSemesterResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping("/user/semester")
public interface UserSemesterController {
    @PostMapping
    public ResponseEntity<UserSemesterResponseDTO> create(
            @RequestBody @Valid CreateUserSemesterDTO userSemesterDto);

    @GetMapping
    public ResponseEntity<List<UserSemesterResponseDTO>> findByAuthenticatedUser();

    @GetMapping("/{id}")
    public ResponseEntity<UserSemesterResponseDTO> findById(@PathVariable Long id);

    @PutMapping("/{id}")
    public ResponseEntity<UserSemesterResponseDTO> update(
            @PathVariable Long id, @RequestBody @Valid UpdateUserSemesterDTO userSemesterDto);

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id);
}
