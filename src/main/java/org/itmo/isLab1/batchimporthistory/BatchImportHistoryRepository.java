package org.itmo.isLab1.batchimporthistory;

import org.itmo.isLab1.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchImportHistoryRepository extends JpaRepository<BatchImportHistory, Integer> {
    Page<BatchImportHistory> findByCreatedByOrderByCreatedAtDesc(User createdBy, Pageable pageable);
    
    Page<BatchImportHistory> findAllByOrderByCreatedAtDesc(Pageable pageable);
}

