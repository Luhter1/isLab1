package org.itmo.isLab1.dragonheads;

import java.util.Optional;

import org.itmo.isLab1.common.framework.CrudRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

public interface DragonHeadRepository extends CrudRepository<DragonHead> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT h FROM DragonHead h WHERE h.id = :id")
    Optional<DragonHead> findByIdWithLock(@Param("id") Integer id);
}
