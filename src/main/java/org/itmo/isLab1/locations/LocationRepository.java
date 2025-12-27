package org.itmo.isLab1.locations;

import org.itmo.isLab1.common.framework.CrudRepository;

public interface LocationRepository extends CrudRepository<Location> {
    boolean existsByXAndYAndZAndNameAndIdNot(Long x, Integer y, Double z, String name, Integer id);
}
