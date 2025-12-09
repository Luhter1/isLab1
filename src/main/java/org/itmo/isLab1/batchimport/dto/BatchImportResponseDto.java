package org.itmo.isLab1.batchimport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchImportResponseDto {
    private int totalOperations;
    private int successfulOperations;
    private int failedOperations;
    private List<OperationResult> results;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OperationResult {
        private int index;
        private boolean success;
        private String message;
    }
}

