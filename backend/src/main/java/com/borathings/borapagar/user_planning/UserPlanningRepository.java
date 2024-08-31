package com.borathings.borapagar.user_planning;

import com.borathings.borapagar.core.AbstractRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlanningRepository extends AbstractRepository<UserPlanningEntity> {
    public Optional<UserPlanningEntity> findByUserIdAndSubjectId(Long userId, Long subjectId);

    public List<UserPlanningEntity> findByUserId(Long userId);
}
