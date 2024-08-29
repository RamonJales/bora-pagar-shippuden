package com.borathings.borapagar.user_interest;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.borathings.borapagar.core.SoftDeletableRepository;

@Repository
public interface UserInterestRepository extends SoftDeletableRepository<UserInterestEntity>{
    public Optional<UserInterestEntity> findByUserIdAndSubjectId(Long userId, Long subjectId);
}
