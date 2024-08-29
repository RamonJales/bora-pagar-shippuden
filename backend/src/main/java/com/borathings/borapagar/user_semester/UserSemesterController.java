package com.borathings.borapagar.user_semester;

import com.borathings.borapagar.user_semester.dto.UserSemesterDTO;
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
    public ResponseEntity<UserSemesterEntity> create(
            @RequestBody @Valid UserSemesterDTO userSemesterDto);

    @GetMapping
    public ResponseEntity<List<UserSemesterEntity>> findByAuthenticatedUser();

    @GetMapping("/{id}")
    public ResponseEntity<UserSemesterEntity> findById(@PathVariable Long id);

    @PutMapping("/{id}")
    public ResponseEntity<UserSemesterEntity> update(
            @PathVariable Long id, @RequestBody @Valid UserSemesterDTO userSemesterDto);

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id);
}
