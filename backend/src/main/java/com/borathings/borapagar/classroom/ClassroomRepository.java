package com.borathings.borapagar.classroom;

import com.borathings.borapagar.core.SoftDeletableRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends SoftDeletableRepository<ClassroomEntity> {}
