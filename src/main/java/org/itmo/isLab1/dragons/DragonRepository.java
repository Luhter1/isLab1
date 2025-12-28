package org.itmo.isLab1.dragons;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.itmo.isLab1.common.framework.CrudRepository;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface DragonRepository extends CrudRepository<Dragon> {
    boolean existsByHead_IdAndIdNot(Integer headId, Integer dragonId);
    
    /**
     * Метод с пессимистичной блокировкой для проверки уникальности headId
     * Блокирует строки, содержащие указанный headId, для предотвращения конкурентных изменений
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT d FROM Dragon d WHERE d.head.id = :headId AND d.id <> :dragonId")
    List<Dragon> findDragonsWithHeadIdExcludingDragonIdForUpdate(@Param("headId") Integer headId, @Param("dragonId") Integer dragonId);
    
    /**
     * Альтернативный метод с ручным FOR UPDATE для пессимистичной блокировки
     */
    @Query(value = "SELECT * FROM dragons WHERE head_id = :headId AND id <> :dragonId FOR UPDATE", nativeQuery = true)
    List<Dragon> findDragonsWithHeadIdExcludingDragonIdNativeForUpdate(@Param("headId") Integer headId, @Param("dragonId") Integer dragonId);
    
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
}
