package com.borathings.borapagar.core;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

/** SoftDeletableRepository */
@NoRepositoryBean
public interface SoftDeletableRepository<M extends SoftDeletableModel>
        extends AbstractRepository<M> {

    @Query("UPDATE #{#entityName} e SET e.deletedAt = CURRENT_TIMESTAMP WHERE e.id = ?1")
    void softDelete(Long id);
}
