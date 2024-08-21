package com.borathings.borapagar.course;

import com.borathings.borapagar.core.SoftDeletableRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends SoftDeletableRepository<CourseEntity> {}
