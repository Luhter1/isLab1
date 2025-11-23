package org.itmo.isLab1.dragons;

import org.springframework.data.jpa.repository.Query;
import org.itmo.isLab1.common.framework.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DragonRepository extends CrudRepository<Dragon> {
    @Query("SELECT AVG(d.age) FROM Dragon d")
    Optional<Double> getAverageAge();

    Optional<Dragon> findTopAgeByAgeIsNotNullOrderByAgeDesc();

    @Query("""
    SELECT d FROM Dragon d
    JOIN d.cave c
    WHERE c.depth = (SELECT MAX(c2.depth) FROM DragonCave c2)
    ORDER BY d.id
    """)
    List<Dragon> findDragonsInDeepestCave();

    default Optional<Dragon> findDragonInDeepestCave() {
        return findDragonsInDeepestCave().stream().findFirst();
    }

//     @Query("SELECT d FROM Dragon d JOIN d.cave dc WHERE dc.depth = (SELECT MAX(c.depth) FROM DragonCave c)")
//     Optional<Dragon> findDragonInDeepestCave();
}
