package org.itmo.isLab1.people;

import org.itmo.isLab1.common.framework.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends CrudRepository<Person> {
    boolean existsByPassportIdAndIdNot(String passportId, Integer id);

    @Query(value = "SELECT pg_advisory_xact_lock(hashtext(?1))", nativeQuery = true)
    void lockByPassportId(@Param("passportId") String passportId);
}
