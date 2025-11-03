package org.itmo.isLab1.common.framework;

import org.itmo.isLab1.common.entity.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CrudRepository<T extends CrudEntity>
  extends BaseRepository<T, Integer>,
          JpaSpecificationExecutor<T> {
}
