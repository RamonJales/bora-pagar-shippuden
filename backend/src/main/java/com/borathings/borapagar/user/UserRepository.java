package com.borathings.borapagar.user;

import com.borathings.borapagar.core.SoftDeletableRepository;
import java.util.Optional;

/** UserRepository */
public interface UserRepository extends SoftDeletableRepository<UserEntity> {
    Optional<UserEntity> findByGoogleId(String googleId);
}
