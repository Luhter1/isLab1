package org.itmo.isLab1.locations;

import org.itmo.isLab1.common.framework.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends CrudRepository<Location> {
    boolean existsByXAndYAndZAndNameAndIdNot(Long x, Integer y, Double z, String name, Integer id);

    @Query(value = "SELECT pg_advisory_xact_lock(hashtext(concat(?1, '|', ?2, '|', ?3, '|', ?4)))", nativeQuery = true)
    void lockByLocation(@Param("x") String x, @Param("y") String y, @Param("z") String z, @Param("name") String name);
}
