package com.borathings.borapagar.user;

import com.borathings.borapagar.core.AbstractRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/** UserRepository */
@Repository
public interface UserRepository extends AbstractRepository<UserEntity> {
    Optional<UserEntity> findByGoogleId(String googleId);
}
