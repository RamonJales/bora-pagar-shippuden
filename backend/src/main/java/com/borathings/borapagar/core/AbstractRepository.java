package com.borathings.borapagar.core;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepository<M extends AbstractModel> extends JpaRepository<M, Long> {
    List<M> findAllByDeletedFalse();
    //Page<M> findAllByDeletedFalseByPage(Pageable pageable);
}
