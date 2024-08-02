package com.borathings.borapagar.user;

import com.borathings.borapagar.core.AbstractRepository;
import java.util.Optional;

/** UserRepository */
public interface UserRepository extends AbstractRepository<UserEntity> {
    Optional<UserEntity> findByGoogleId(String googleId);
}
