package com.borathings.borapagar.user_interest;

import com.borathings.borapagar.core.SoftDeletableRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterestRepository extends SoftDeletableRepository<UserInterestEntity> {
    public Optional<UserInterestEntity> findByUserIdAndSubjectId(Long userId, Long subjectId);

    public List<UserInterestEntity> findByUserIdAndDeletedAtIsNull(Long userId);
}
