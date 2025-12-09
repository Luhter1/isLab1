package org.itmo.isLab1.batchimport.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.itmo.isLab1.batchimport.dto.BatchImportResponseDto;
import org.itmo.isLab1.batchimport.dto.BatchOperationDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BatchImportService {
    private final JsonSchemaValidationService validationService;
    private final ObjectMapper objectMapper;
    private final CoordinateService coordinateService;
    private final DragonCaveService dragonCaveService;
    private final DragonHeadService dragonHeadService;
    private final DragonService dragonService;
    private final LocationService locationService;
    private final PersonService personService;

    public BatchImportService(
            JsonSchemaValidationService validationService,
            ObjectMapper objectMapper,
            CoordinateService coordinateService,
            DragonCaveService dragonCaveService,
            DragonHeadService dragonHeadService,
            DragonService dragonService,
            LocationService locationService,
            PersonService personService
    ) {
        this.validationService = validationService;
        this.objectMapper = objectMapper;
        this.coordinateService = coordinateService;
        this.dragonCaveService = dragonCaveService;
        this.dragonHeadService = dragonHeadService;
        this.dragonService = dragonService;
        this.locationService = locationService;
        this.personService = personService;
    }

    @Transactional(rollbackFor = Exception.class)
    public BatchImportResponseDto importBatch(JsonNode jsonNode) {
        // Validate schema
        var validationMessages = validationService.validateBatchOperation(jsonNode);
        if (!validationMessages.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder("Schema validation failed: ");
            validationMessages.forEach(msg -> errorMsg.append(msg.getMessage()).append("; "));
            throw new IllegalArgumentException(errorMsg.toString());
        }

        List<BatchOperationDto> operations = parseOperations(jsonNode);
        List<BatchImportResponseDto.OperationResult> results = new ArrayList<>();
        int successfulCount = 0;
        int failedCount = 0;

        for (int i = 0; i < operations.size(); i++) {
            BatchOperationDto operation = operations.get(i);
            try {
                processOperation(operation);
                results.add(new BatchImportResponseDto.OperationResult(i, true, "Success"));
                successfulCount++;
            } catch (Exception e) {
                results.add(new BatchImportResponseDto.OperationResult(i, false, e.getMessage()));
                failedCount++;
                // Rollback entire transaction on any error
                throw new RuntimeException("Operation " + i + " failed: " + e.getMessage(), e);
            }
        }

        return new BatchImportResponseDto(
                operations.size(),
                successfulCount,
                failedCount,
                results
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
        BatchOperationDto.ResourceType resourceType = BatchOperationDto.ResourceType.fromString(operation.getResourceType());
        BatchOperationDto.OperationType operationType = operation.getType();

        switch (resourceType) {
            case COORDINATES:
                processCoordinateOperation(operationType, operation);
                break;
            case DRAGON_CAVES:
                processDragonCaveOperation(operationType, operation);
                break;
            case DRAGON_HEADS:
                processDragonHeadOperation(operationType, operation);
                break;
            case DRAGONS:
                processDragonOperation(operationType, operation);
                break;
            case LOCATIONS:
                processLocationOperation(operationType, operation);
                break;
            case PEOPLE:
                processPersonOperation(operationType, operation);
                break;
        }
    }

    private void processCoordinateOperation(BatchOperationDto.OperationType type, BatchOperationDto operation) {
        switch (type) {
            case CREATE:
                CoordinateCreateDto createDto = objectMapper.convertValue(operation.getBody(), CoordinateCreateDto.class);
                coordinateService.create(createDto);
                break;
            case UPDATE:
                CoordinateUpdateDto updateDto = objectMapper.convertValue(operation.getBody(), CoordinateUpdateDto.class);
                coordinateService.update(updateDto, operation.getResourceId());
                break;
            case DELETE:
                coordinateService.delete(operation.getResourceId());
                break;
        }
    }

    private void processDragonCaveOperation(BatchOperationDto.OperationType type, BatchOperationDto operation) {
        switch (type) {
            case CREATE:
                DragonCaveCreateDto createDto = objectMapper.convertValue(operation.getBody(), DragonCaveCreateDto.class);
                dragonCaveService.create(createDto);
                break;
            case UPDATE:
                DragonCaveUpdateDto updateDto = objectMapper.convertValue(operation.getBody(), DragonCaveUpdateDto.class);
                dragonCaveService.update(updateDto, operation.getResourceId());
                break;
            case DELETE:
                dragonCaveService.delete(operation.getResourceId());
                break;
        }
    }

    private void processDragonHeadOperation(BatchOperationDto.OperationType type, BatchOperationDto operation) {
        switch (type) {
            case CREATE:
                DragonHeadCreateDto createDto = objectMapper.convertValue(operation.getBody(), DragonHeadCreateDto.class);
                dragonHeadService.create(createDto);
                break;
            case UPDATE:
                DragonHeadUpdateDto updateDto = objectMapper.convertValue(operation.getBody(), DragonHeadUpdateDto.class);
                dragonHeadService.update(updateDto, operation.getResourceId());
                break;
            case DELETE:
                dragonHeadService.delete(operation.getResourceId());
                break;
        }
    }

    private void processLocationOperation(BatchOperationDto.OperationType type, BatchOperationDto operation) {
        switch (type) {
            case CREATE:
                LocationCreateDto createDto = objectMapper.convertValue(operation.getBody(), LocationCreateDto.class);
                locationService.create(createDto);
                break;
            case UPDATE:
                LocationUpdateDto updateDto = objectMapper.convertValue(operation.getBody(), LocationUpdateDto.class);
                locationService.update(updateDto, operation.getResourceId());
                break;
            case DELETE:
                locationService.delete(operation.getResourceId());
                break;
        }
    }

    private void processPersonOperation(BatchOperationDto.OperationType type, BatchOperationDto operation) {
        Map<String, Object> body = operation.getBody();
        
        // Handle nested location object
        if (body.containsKey("location") && body.get("location") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> locationMap = (Map<String, Object>) body.get("location");
            LocationCreateDto locationDto = objectMapper.convertValue(locationMap, LocationCreateDto.class);
            var createdLocation = locationService.create(locationDto);
            body.put("locationId", createdLocation.getId());
            body.remove("location");
        }

        switch (type) {
            case CREATE:
                PersonCreateDto createDto = objectMapper.convertValue(body, PersonCreateDto.class);
                personService.create(createDto);
                break;
            case UPDATE:
                PersonUpdateDto updateDto = objectMapper.convertValue(body, PersonUpdateDto.class);
                personService.update(updateDto, operation.getResourceId());
                break;
            case DELETE:
                personService.delete(operation.getResourceId());
                break;
        }
    }

    private void processDragonOperation(BatchOperationDto.OperationType type, BatchOperationDto operation) {
        Map<String, Object> body = operation.getBody();
        
        // Handle nested objects - create them first in the same transaction
        if (body.containsKey("coordinates") && body.get("coordinates") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> coordinatesMap = (Map<String, Object>) body.get("coordinates");
            CoordinateCreateDto coordinatesDto = objectMapper.convertValue(coordinatesMap, CoordinateCreateDto.class);
            var createdCoordinates = coordinateService.create(coordinatesDto);
            body.put("coordinatesId", createdCoordinates.getId());
            body.remove("coordinates");
        }

        if (body.containsKey("head") && body.get("head") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> headMap = (Map<String, Object>) body.get("head");
            DragonHeadCreateDto headDto = objectMapper.convertValue(headMap, DragonHeadCreateDto.class);
            var createdHead = dragonHeadService.create(headDto);
            body.put("headId", createdHead.getId());
            body.remove("head");
        }

        if (body.containsKey("cave") && body.get("cave") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> caveMap = (Map<String, Object>) body.get("cave");
            DragonCaveCreateDto caveDto = objectMapper.convertValue(caveMap, DragonCaveCreateDto.class);
            var createdCave = dragonCaveService.create(caveDto);
            body.put("caveId", createdCave.getId());
            body.remove("cave");
        }

        if (body.containsKey("killer") && body.get("killer") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> killerMap = (Map<String, Object>) body.get("killer");
            // Killer is a Person, which might have nested location
            if (killerMap.containsKey("location") && killerMap.get("location") instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> locationMap = (Map<String, Object>) killerMap.get("location");
                LocationCreateDto locationDto = objectMapper.convertValue(locationMap, LocationCreateDto.class);
                var createdLocation = locationService.create(locationDto);
                killerMap.put("locationId", createdLocation.getId());
                killerMap.remove("location");
            }
            PersonCreateDto killerDto = objectMapper.convertValue(killerMap, PersonCreateDto.class);
            var createdKiller = personService.create(killerDto);
            body.put("killerId", createdKiller.getId());
            body.remove("killer");
        }

        switch (type) {
            case CREATE:
                DragonCreateDto createDto = objectMapper.convertValue(body, DragonCreateDto.class);
                dragonService.create(createDto);
                break;
            case UPDATE:
                DragonUpdateDto updateDto = objectMapper.convertValue(body, DragonUpdateDto.class);
                dragonService.update(updateDto, operation.getResourceId());
                break;
            case DELETE:
                dragonService.delete(operation.getResourceId());
                break;
        }
    }
}

