package com.borathings.borapagar.subject;

import com.borathings.borapagar.core.SoftDeletableRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface SubjectRepository extends SoftDeletableRepository<SubjectEntity> {
    ResponseEntity<List<SubjectEntity>> findByNameContainingIgnoreCase(String name);
}
