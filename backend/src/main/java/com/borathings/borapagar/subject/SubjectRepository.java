package com.borathings.borapagar.subject;

import com.borathings.borapagar.core.SoftDeletableRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends SoftDeletableRepository<SubjectEntity> {}
