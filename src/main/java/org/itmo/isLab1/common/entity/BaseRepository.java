package org.itmo.isLab1.common.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, Type> 
  extends JpaRepository<T, Type>{
}
