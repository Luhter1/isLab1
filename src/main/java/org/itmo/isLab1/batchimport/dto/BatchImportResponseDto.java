package org.itmo.isLab1.batchimport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchImportResponseDto {
    private int successfulOperations;
    private int failedOperations;
}

