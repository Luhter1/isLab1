package org.itmo.isLab1.batchimporthistory;

import lombok.AllArgsConstructor;
import org.itmo.isLab1.batchimporthistory.dto.BatchImportHistoryDto;
import org.itmo.isLab1.batchimporthistory.enums.ImportStatus;
import org.itmo.isLab1.events.EventService;
import org.itmo.isLab1.events.EventType;
import org.itmo.isLab1.users.User;
import org.itmo.isLab1.users.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BatchImportHistoryService {
    private final BatchImportHistoryRepository repository;
    private final UserService userService;
    private EventService<BatchImportHistory> eventService;

    public Page<BatchImportHistoryDto> getHistory(Pageable pageable, boolean isAdmin) {
        Page<BatchImportHistory> history;
        
        if (isAdmin) {
            // Администратор видит всю историю
            history = repository.findAllByOrderByCreatedAtDesc(pageable);
        } else {
            // Обычный пользователь видит только свои операции
            User currentUser = userService.getCurrentUser();
            history = repository.findByCreatedByOrderByCreatedAtDesc(currentUser, pageable);
        }
        
        return history.map(this::mapToDto);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveImportHistory(int successfulOperations, ImportStatus status) {
        var currentUser = userService.getCurrentUser();
        var history = BatchImportHistory.builder()
                .createdBy(currentUser)
                .status(status)
                .successfulOperations(successfulOperations)
                .build();
        repository.save(history);
        eventService.notify(EventType.CREATE, history);
    }

    /**
     * Сохраняет историю импорта с указанием пути к файлу.
     * Использует REQUIRES_NEW для гарантированного сохранения истории
     * независимо от результата основной транзакции импорта.
     *
     * @param successfulOperations количество успешных операций
     * @param status статус импорта
     * @param filePath путь к файлу в MinIO
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveImportHistory(int successfulOperations, ImportStatus status, String filePath) {
        var currentUser = userService.getCurrentUser();
        var history = BatchImportHistory.builder()
                .createdBy(currentUser)
                .status(status)
                .successfulOperations(successfulOperations)
                .filePath(filePath)
                .build();
        repository.save(history);
        eventService.notify(EventType.CREATE, history);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateImportHistory(int historyId, ImportStatus status, int successfulOperations) {
        var history = repository.findById(historyId)
                .orElseThrow(() -> new IllegalArgumentException("Import history not found with id: " + historyId));
        history.setStatus(status);
        history.setSuccessfulOperations(successfulOperations);
        repository.save(history);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateImportHistory(int historyId, ImportStatus status, int successfulOperations, String filePath) {
        var history = repository.findById(historyId)
                .orElseThrow(() -> new IllegalArgumentException("Import history not found with id: " + historyId));
        history.setStatus(status);
        history.setSuccessfulOperations(successfulOperations);
        history.setFilePath(filePath);
        repository.save(history);
    }

    private BatchImportHistoryDto mapToDto(BatchImportHistory history) {
        return new BatchImportHistoryDto(
                history.getId(),
                history.getCreatedBy(),
                history.getCreatedAt(),
                history.getStatus(),
                history.getSuccessfulOperations(),
                history.getFilePath()
        );
    }
}