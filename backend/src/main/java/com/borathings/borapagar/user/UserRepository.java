package com.borathings.borapagar.user;

import com.borathings.borapagar.core.SoftDeletableRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/** UserRepository */
public interface UserRepository extends SoftDeletableRepository<UserEntity> {
    Optional<UserEntity> findByGoogleId(String googleId);
}
