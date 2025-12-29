package org.itmo.isLab1.batchimport.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SchemaValidatorsConfig;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class JsonSchemaValidationService {
    
    private static final String BATCH_SCHEMA_PATH = "schemas/batch-operation.json";
    
    private final ObjectMapper objectMapper;
    private JsonSchema batchOperationSchema;

    public JsonSchemaValidationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() throws IOException {
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.builder(
                JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7))
                .schemaMappers(schemaMappers -> schemaMappers
                        .mapPrefix("classpath://", "resource:"))
                .build();

        // Загружаем основную схему batch-operation
        ClassPathResource schemaResource = new ClassPathResource(BATCH_SCHEMA_PATH);
        if (!schemaResource.exists()) {
            throw new IOException("Schema file not found: " + BATCH_SCHEMA_PATH);
        }
        
        try (InputStream schemaStream = schemaResource.getInputStream()) {
            JsonNode schemaNode = objectMapper.readTree(schemaStream);
            
            // Указываем base URI для корректного разрешения относительных $ref
            SchemaValidatorsConfig config = new SchemaValidatorsConfig();
            
            batchOperationSchema = schemaFactory.getSchema(
                    URI.create("resource:/" + BATCH_SCHEMA_PATH),
                    schemaNode,
                    config
            );
        }
    }

    public Set<ValidationMessage> validateBatchOperation(JsonNode jsonNode) {
        if (batchOperationSchema == null) {
            Set<ValidationMessage> errors = new LinkedHashSet<>();
            errors.add(ValidationMessage.builder()
                    .message("Schema validation service not initialized")
                    .build());
            return errors;
        }
        
        return batchOperationSchema.validate(jsonNode);
    }
}