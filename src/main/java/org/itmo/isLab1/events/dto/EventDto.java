package org.itmo.isLab1.events.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.itmo.isLab1.common.framework.dto.ClientDto;
import org.itmo.isLab1.events.EventType;
import org.itmo.isLab1.events.resources.ResourceType;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class EventDto<T> implements ClientDto{

    public EventType eventType;
    public ResourceType resourceType;
    public Integer resourceId;
    public String requestUUID;
    public T entity;

    public Map<String, Object> getPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("eventType", eventType);
        payload.put("resourceType", resourceType);
        payload.put("resourceId", resourceId);
        payload.put("requestUUID", requestUUID);        
        if (eventType != EventType.DELETE) {
            payload.put("entity", entity);
        }

        return payload;
    }
}