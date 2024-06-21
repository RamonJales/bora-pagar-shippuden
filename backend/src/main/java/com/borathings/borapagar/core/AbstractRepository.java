package com.borathings.borapagar.core;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepository<M extends AbstractModel> extends JpaRepository<M, Long> {
    List<M> findAllActive();

    Page<M> findAllActiveByPage(Pageable pageable);
}
