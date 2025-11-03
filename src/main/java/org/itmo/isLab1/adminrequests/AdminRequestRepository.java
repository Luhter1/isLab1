package org.itmo.isLab1.adminrequests;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.itmo.isLab1.common.entity.BaseRepository;
import org.itmo.isLab1.users.User;

import java.util.Optional;

public interface AdminRequestRepository
    extends BaseRepository<AdminRequest, Integer>,
            JpaSpecificationExecutor<AdminRequest>{
    Page<AdminRequest> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<AdminRequest> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    Page<AdminRequest> findAllByStatusOrderByCreatedAtDesc(Status status, Pageable pageable);
    Optional<AdminRequest> findByStatusAndUserOrderByCreatedAtDesc(Status status, User user);
}
