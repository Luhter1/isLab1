package org.itmo.isLab1.batchimport.service;

import com.networknt.schema.ValidationMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.itmo.isLab1.batchimport.dto.BatchImportResponseDto;
import org.itmo.isLab1.batchimport.dto.BatchOperationDto;
import org.itmo.isLab1.batchimporthistory.BatchImportHistoryService;
import org.itmo.isLab1.batchimporthistory.enums.ImportStatus;
import org.itmo.isLab1.coordinates.CoordinateService;
import org.itmo.isLab1.coordinates.dto.CoordinateCreateDto;
import org.itmo.isLab1.coordinates.dto.CoordinateUpdateDto;
import org.itmo.isLab1.dragoncaves.DragonCaveService;
import org.itmo.isLab1.dragoncaves.dto.DragonCaveCreateDto;
import org.itmo.isLab1.dragoncaves.dto.DragonCaveUpdateDto;
import org.itmo.isLab1.dragonheads.DragonHeadService;
import org.itmo.isLab1.dragonheads.dto.DragonHeadCreateDto;
import org.itmo.isLab1.dragonheads.dto.DragonHeadUpdateDto;
import org.itmo.isLab1.dragons.DragonService;
import org.itmo.isLab1.dragons.dto.DragonCreateDto;
import org.itmo.isLab1.dragons.dto.DragonUpdateDto;
import org.itmo.isLab1.locations.LocationService;
import org.itmo.isLab1.locations.dto.LocationCreateDto;
import org.itmo.isLab1.locations.dto.LocationUpdateDto;
import org.itmo.isLab1.people.PersonService;
import org.itmo.isLab1.people.dto.PersonCreateDto;
import org.itmo.isLab1.people.dto.PersonUpdateDto;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BatchImportService {
    private final JsonSchemaValidationService validationService;
    private final ObjectMapper objectMapper;
    private final CoordinateService coordinateService;
    private final DragonCaveService dragonCaveService;
    private final DragonHeadService dragonHeadService;
    private final DragonService dragonService;
    private final LocationService locationService;
    private final PersonService personService;
    private final BatchImportHistoryService historyService;

    @Transactional(
        rollbackFor = Exception.class,
        propagation = Propagation.REQUIRED, 
        isolation = Isolation.SERIALIZABLE
    )
    @Retryable(
        retryFor = { SQLException.class, CannotAcquireLockException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 100)
    )
    public BatchImportResponseDto importBatch(JsonNode jsonNode) {
        ImportStatus status = ImportStatus.FAILED;
        Set<ValidationMessage> validationMessages = validationService.validateBatchOperation(jsonNode);
        if (!validationMessages.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder("JSON Schema validation failed: ");
            validationMessages.forEach(msg -> errorMsg.append(msg.getMessage()).append("; "));
            historyService.saveImportHistory(0, status);
            throw new IllegalArgumentException(errorMsg.toString());
        }

        List<BatchOperationDto> operations = parseOperations(jsonNode);
        int successfulCount = 0;
        status = ImportStatus.SUCCESS;

        try {
            for (int i = 0; i < operations.size(); i++) {
                BatchOperationDto operation = operations.get(i);
                try {
                    processOperation(operation);
                    successfulCount++;
                } catch (Exception e) {
                    status = ImportStatus.FAILED;
                    successfulCount = 0;
                    throw new RuntimeException("Operation " + i + " failed: " + e.getMessage(), e);
                }
            }
        } finally {
            // Сохраняем историю импорта независимо от результата
            historyService.saveImportHistory(successfulCount, status);
        }

        return new BatchImportResponseDto(
                successfulCount,
                operations.size() - successfulCount
        );
    }

    private List<BatchOperationDto> parseOperations(JsonNode jsonNode) {
        List<BatchOperationDto> operations = new ArrayList<>();
        if (jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
                operations.add(objectMapper.convertValue(node, BatchOperationDto.class));
            }
        }
        return operations;
    }

    private void processOperation(BatchOperationDto operation) {
        var resourceType = BatchOperationDto.ResourceType.fromString(operation.getResourceType());
        var operationType = operation.getType();

        switch (resourceType) {
            case COORDINATES -> processCoordinateOperation(operationType, operation);
            case DRAGON_CAVES -> processDragonCaveOperation(operationType, operation);
            case DRAGON_HEADS -> processDragonHeadOperation(operationType, operation);
            case DRAGONS -> processDragonOperation(operationType, operation);
            case LOCATIONS -> processLocationOperation(operationType, operation);
            case PEOPLE -> processPersonOperation(operationType, operation);
            default -> throw new IllegalArgumentException("Unknown resource type: " + resourceType);
        }
    }

    private void processCoordinateOperation(BatchOperationDto.OperationType type, BatchOperationDto operation) {
        switch (type) {
            case CREATE -> coordinateService.create(convertBody(operation, CoordinateCreateDto.class));
            case UPDATE -> coordinateService.update(convertBody(operation, CoordinateUpdateDto.class), operation.getResourceId());
            case DELETE -> coordinateService.delete(operation.getResourceId());
        }
    }

    private void processDragonCaveOperation(BatchOperationDto.OperationType type, BatchOperationDto operation) {
        switch (type) {
            case CREATE -> dragonCaveService.create(convertBody(operation, DragonCaveCreateDto.class));
            case UPDATE -> dragonCaveService.update(convertBody(operation, DragonCaveUpdateDto.class), operation.getResourceId());
            case DELETE -> dragonCaveService.delete(operation.getResourceId());
        }
    }

    private void processDragonHeadOperation(BatchOperationDto.OperationType type, BatchOperationDto operation) {
        switch (type) {
            case CREATE -> dragonHeadService.create(convertBody(operation, DragonHeadCreateDto.class));
            case UPDATE -> dragonHeadService.update(convertBody(operation, DragonHeadUpdateDto.class), operation.getResourceId());
            case DELETE -> dragonHeadService.delete(operation.getResourceId());
        }
    }

    private void processLocationOperation(BatchOperationDto.OperationType type, BatchOperationDto operation) {
        switch (type) {
            case CREATE -> locationService.create(convertBody(operation, LocationCreateDto.class));
            case UPDATE -> locationService.update(convertBody(operation, LocationUpdateDto.class), operation.getResourceId());
            case DELETE -> locationService.delete(operation.getResourceId());
        }
    }

    private void processPersonOperation(BatchOperationDto.OperationType type, BatchOperationDto operation) {
        Map<String, Object> body = operation.getBody();
        
        // Рекурсивное создание Location, если она есть в теле
        if (body.containsKey("location")) {
            LocationCreateDto dto = extractNestedDto(body.get("location"), LocationCreateDto.class);
            var created = locationService.create(dto);
            replaceNestedWithId(body, "location", "locationId", created.getId());
        }

        switch (type) {
            case CREATE -> personService.create(extractNestedDto(body, PersonCreateDto.class));
            case UPDATE -> personService.update(extractNestedDto(body, PersonUpdateDto.class), operation.getResourceId());
            case DELETE -> personService.delete(operation.getResourceId());
        }
    }

    private void processDragonOperation(BatchOperationDto.OperationType type, BatchOperationDto operation) {
        Map<String, Object> body = operation.getBody();

        // 1. Обработка координат
        if (body.containsKey("coordinates")) {
            CoordinateCreateDto dto = extractNestedDto(body.get("coordinates"), CoordinateCreateDto.class);
            var created = coordinateService.create(dto);
            replaceNestedWithId(body, "coordinates", "coordinatesId", created.getId());
        }

        // 2. Обработка головы
        if (body.containsKey("head")) {
            DragonHeadCreateDto dto = extractNestedDto(body.get("head"), DragonHeadCreateDto.class);
            var created = dragonHeadService.create(dto);
            replaceNestedWithId(body, "head", "headId", created.getId());
        }

        // 3. Обработка пещеры
        if (body.containsKey("cave")) {
            DragonCaveCreateDto dto = extractNestedDto(body.get("cave"), DragonCaveCreateDto.class);
            var created = dragonCaveService.create(dto);
            replaceNestedWithId(body, "cave", "caveId", created.getId());
        }

        // 4. Обработка убийцы
        if (body.containsKey("killer")) {
            Map<String, Object> killerMap = extractMap(body.get("killer"));
            
            if (killerMap.containsKey("location")) {
                LocationCreateDto dto = extractNestedDto(killerMap.get("location"), LocationCreateDto.class);
                var created = locationService.create(dto);
                replaceNestedWithId(killerMap, "location", "locationId", created.getId());
            }

            PersonCreateDto killerDto = extractNestedDto(killerMap, PersonCreateDto.class);
            var createdKiller = personService.create(killerDto);
            
            replaceNestedWithId(body, "killer", "killerId", createdKiller.getId());
        }

        switch (type) {
            case CREATE -> dragonService.create(extractNestedDto(body, DragonCreateDto.class));
            case UPDATE -> dragonService.update(extractNestedDto(body, DragonUpdateDto.class), operation.getResourceId());
            case DELETE -> dragonService.delete(operation.getResourceId());
        }
    }

    private <T> T convertBody(BatchOperationDto operation, Class<T> clazz) {
        return objectMapper.convertValue(operation.getBody(), clazz);
    }

    private <T> T extractNestedDto(Object nestedObject, Class<T> clazz) {
        return objectMapper.convertValue(nestedObject, clazz);
    }
    
    @SuppressWarnings("unchecked")
    private Map<String, Object> extractMap(Object object) {
        return objectMapper.convertValue(object, Map.class);
    }

    private void replaceNestedWithId(Map<String, Object> map, String objectKey, String idKey, int idValue) {
        map.put(idKey, idValue);
        map.remove(objectKey);
    }
}

