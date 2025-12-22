package org.itmo.isLab1.batchimporthistory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.itmo.isLab1.batchimporthistory.enums.ImportStatus;
import org.itmo.isLab1.users.User;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchImportHistoryDto {
    private int id;
    private User createdBy;
    private ZonedDateTime createdAt;
    private ImportStatus status;
    private Integer successfulOperations;
}

