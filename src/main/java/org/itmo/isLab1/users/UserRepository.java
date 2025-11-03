package org.itmo.isLab1.users;

import org.itmo.isLab1.common.entity.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
  Optional<User> findByUsername(String username);
  boolean existsByUsername(String username);
}