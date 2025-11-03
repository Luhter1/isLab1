package org.itmo.isLab1.events;

import org.itmo.isLab1.common.entity.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends BaseRepository<Event, Integer> {
}