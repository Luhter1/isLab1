package org.itmo.isLab1.batchimport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.itmo.isLab1.batchimport.dto.BatchImportResponseDto;
import org.itmo.isLab1.batchimport.service.BatchImportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/batch-import", produces = MediaType.APPLICATION_JSON_VALUE)
public class BatchImportController {
    private final BatchImportService batchImportService;
    private final ObjectMapper objectMapper;

    public BatchImportController(BatchImportService batchImportService, ObjectMapper objectMapper) {
        this.batchImportService = batchImportService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<BatchImportResponseDto> importFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(file.getInputStream());
            BatchImportResponseDto response = batchImportService.importBatch(jsonNode);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse file: " + e.getMessage(), e);
        }
    }
}

