package com.borathings.borapagar.user_semester;

import com.borathings.borapagar.core.SoftDeletableRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSemesterRepository extends SoftDeletableRepository<UserSemesterEntity> {
    public List<UserSemesterEntity> findByUserId(Long userId);
}
