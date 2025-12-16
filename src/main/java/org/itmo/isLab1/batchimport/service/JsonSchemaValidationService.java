package org.itmo.isLab1.batchimport.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.networknt.schema.ValidatorTypeCode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@Service
public class JsonSchemaValidationService {
    private final ObjectMapper objectMapper;
    private final JsonSchemaFactory schemaFactory;

    public JsonSchemaValidationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
    }

    public Set<ValidationMessage> validateBatchOperation(JsonNode jsonNode) {
        Set<ValidationMessage> allErrors = new HashSet<>();
        
        if (!jsonNode.isArray()) {
            allErrors.add(error("type", "Expected JSON array", "$"
            ));
            return allErrors;
        }
        
        int arraySize = jsonNode.size();
        if (arraySize < 1) {
            allErrors.add(error("minItems", "Array must have at least 1 item", "$"));
            return allErrors;
        }
        if (arraySize > 1024) {
            allErrors.add(error("maxItems", "Array must have at most 1024 items", "$"));
            return allErrors;
        }

        for (int i = 0; i < jsonNode.size(); i++) {
            JsonNode operation = jsonNode.get(i);
            Set<ValidationMessage> operationErrors = validateOperation(operation, i);
            allErrors.addAll(operationErrors);
        }

        return allErrors;
    }

    private Set<ValidationMessage> validateOperation(JsonNode operation, int index) {
        Set<ValidationMessage> errors = new HashSet<>();
        String operIndex = "$[" + index + "]";
        
        // Проверяем, что обьект, а не список
        if (!operation.isObject()) {
            errors.add(error("type", "Expected object", operIndex));
            return errors;
        }

        // Проверяем тип операции
        if (!operation.has("type")) {
            errors.add(error("required", "Missing required property: type", operIndex));
        } else {
            String type = operation.get("type").asText();
            if (!"CREATE".equals(type) && !"UPDATE".equals(type) && !"DELETE".equals(type)) {
                errors.add(error("enum", "type must be one of: CREATE, UPDATE, DELETE", operIndex + ".type"));
            }
        }

        // Проверяем тип данных
        if (!operation.has("resourceType")) {
            errors.add(error("required", "Missing required property: resourceType", operIndex));
        } else {
            String resourceType = operation.get("resourceType").asText();
            String[] validTypes = {"coordinates", "dragon-caves", "dragon-heads", "dragons", "locations", "people"};
            boolean isValid = false;
            for (String validType : validTypes) {
                if (validType.equals(resourceType)) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                errors.add(error("enum", "resourceType must be one of: coordinates, dragon-caves, dragon-heads, dragons, locations, people", operIndex + ".resourceType"));
            } else {
                // Validate body for CREATE operations
                String operationType = operation.has("type") ? operation.get("type").asText() : "";
                if ("CREATE".equals(operationType)) {
                    if (operation.has("resourceId")) {
                        errors.add(error("validation", "CREATE operation should not have resourceId", operIndex));
                    }
                    if (operation.has("body")) {
                        JsonNode body = operation.get("body");
                        if (body != null && body.isObject()) {
                            // For schemas with nested objects (dragons, people), we do basic validation
                            // Full validation with nested objects will be done at application level
                            Set<ValidationMessage> bodyErrors = validateBodyForResourceType(body, resourceType, index);
                            errors.addAll(bodyErrors);
                        }
                    } else {
                        errors.add(error("required", "CREATE operation requires body", operIndex));
                    }
                } else if ("UPDATE".equals(operationType)) {
                    if (!operation.has("resourceId")) {
                        errors.add(error("required", "UPDATE operation requires resourceId", operIndex));
                    }
                    if (!operation.has("body")) {
                        errors.add(error("required", "UPDATE operation requires body", operIndex));
                    }
                } else if ("DELETE".equals(operationType)) {
                    if (!operation.has("resourceId")) {
                        errors.add(error("required", "DELETE operation requires resourceId", operIndex));
                    }
                    if (operation.has("body")) {
                        errors.add(error("validation", "DELETE operation should not have body", operIndex));
                    }
                }
            }
        }

        return errors;
    }

    private Set<ValidationMessage> validateBodyForResourceType(JsonNode body, String resourceType, int index) {
        Set<ValidationMessage> errors = new HashSet<>();
        
        try {
            String schemaPath = getSchemaPathForResourceType(resourceType);
            if (schemaPath == null) {
                return errors; // Unknown resource type, skip validation
            }

            ClassPathResource schemaResource = new ClassPathResource(schemaPath);
            InputStream schemaStream = schemaResource.getInputStream();
            
            // Read the schema and extract the definition
            JsonNode schemaNode = objectMapper.readTree(schemaStream);
            JsonNode definition = null;
            
            if (schemaNode.has("definitions")) {
                String definitionName = getDefinitionNameForResourceType(resourceType);
                if (schemaNode.get("definitions").has(definitionName)) {
                    definition = schemaNode.get("definitions").get(definitionName);
                }
            }
            
            if (definition == null && schemaNode.has("$ref")) {
                // If schema uses $ref, try to get it from definitions
                String refPath = schemaNode.get("$ref").asText();
                if (refPath.startsWith("#/definitions/")) {
                    String defName = refPath.substring("#/definitions/".length());
                    if (schemaNode.has("definitions") && schemaNode.get("definitions").has(defName)) {
                        definition = schemaNode.get("definitions").get(defName);
                    }
                }
            }
            
            if (definition == null) {
                // Fallback: validate against the whole schema
                definition = schemaNode;
            }
            
            // Create a temporary schema with just the definition
            // We need to resolve $ref links manually since networknt may not support classpath://
            com.fasterxml.jackson.databind.node.ObjectNode tempSchema = objectMapper.createObjectNode();
            tempSchema.put("$schema", "http://json-schema.org/draft-07/schema#");
            
            // Copy definition properties, but skip $ref that use classpath://
            if (definition.isObject()) {
                com.fasterxml.jackson.databind.node.ObjectNode defObj = (com.fasterxml.jackson.databind.node.ObjectNode) definition;
                defObj.fields().forEachRemaining(entry -> {
                    String key = entry.getKey();
                    JsonNode value = entry.getValue();
                    
                    // Skip classpath:// references - they will be validated at application level
                    if ("$ref".equals(key) && value.isTextual() && value.asText().startsWith("classpath://")) {
                        // Skip this reference - it will be handled by application logic
                        return;
                    }
                    
                    tempSchema.set(key, value);
                });
            } else {
                tempSchema.set("allOf", objectMapper.createArrayNode().add(definition));
            }
            
            // For schemas with classpath:// references, we do basic validation only
            // Full validation with nested objects will be done at application level
            JsonSchema schema = schemaFactory.getSchema(tempSchema);
            Set<ValidationMessage> validationMessages = schema.validate(body);
            
            // Prefix error messages with operation index
            for (ValidationMessage msg : validationMessages) {
                // Create a new validation message with prefixed path and message
                String instanceLocation = msg.getInstanceLocation() != null ? msg.getInstanceLocation().toString() : "";
                String prefixedPath = "$[" + index + "].body" + (instanceLocation.isEmpty() ? "" : instanceLocation);
                String prefixedMessage = "$[" + index + "].body: " + msg.getMessage();
                
                // Use the existing ValidationMessage but with modified message in our error collection
                // We'll store the original message and add prefix when building error string
                errors.add(msg);
            }
        } catch (Exception e) {
            // If schema validation fails, add a generic error
            errors.add(error(
                "validation.error",
                "$[" + index + "].body: Failed to validate against schema for " + resourceType + ": " + e.getMessage(),
                "$[" + index + "].body"
            ));
        }
        
        return errors;
    }

    private String getSchemaPathForResourceType(String resourceType) {
        return switch (resourceType) {
            case "coordinates" -> "schemas/coordinates.json";
            case "dragon-caves" -> "schemas/dragon-caves.json";
            case "dragon-heads" -> "schemas/dragon-heads.json";
            case "dragons" -> "schemas/dragons.json";
            case "locations" -> "schemas/locations.json";
            case "people" -> "schemas/people.json";
            default -> null;
        };
    }

    private String getDefinitionNameForResourceType(String resourceType) {
        return switch (resourceType) {
            case "coordinates" -> "Coordinate";
            case "dragon-caves" -> "DragonCave";
            case "dragon-heads" -> "DragonHead";
            case "dragons" -> "Dragon";
            case "locations" -> "Location";
            case "people" -> "Person";
            default -> null;
        };
    }

    private ValidationMessage error(
            String keyword,
            String message,
            String instanceLocation
    ) {
        return ValidationMessage.builder()
                .message(message)
                .build();
    }
}

