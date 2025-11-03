package org.itmo.isLab1.events;

import lombok.RequiredArgsConstructor;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.itmo.isLab1.common.entity.BaseEntity;
import org.itmo.isLab1.common.entity.BaseService;
import org.itmo.isLab1.common.entity.ResourceExtractor;
import org.itmo.isLab1.common.ws.WebSocketHandler;
import org.itmo.isLab1.events.dto.EventDto;
import org.itmo.isLab1.events.resources.ResourceType;
import org.itmo.isLab1.users.User;
import org.itmo.isLab1.users.UserService;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class EventService<T extends BaseEntity> implements BaseService{
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    private final EventRepository repository;
    private final WebSocketHandler<T> webSocketHandler;
    private final UserService userService;
    private final ResourceExtractor resourceExtractor;
    private final HttpServletRequest httpServletRequest;

    @Transactional
    public Event create(EventDto<?> eventDto, @Nullable User creator) {
        logger.info("Event({}): {}-{} by User#{}",
            eventDto.getEventType(),
            eventDto.getResourceType(),
            eventDto.getResourceId(),
            creator
        );

        var event = new Event();
        event.setResourceId(eventDto.getResourceId());
        event.setResourceType(eventDto.getResourceType());
        event.setType(eventDto.getEventType());
        event.setCreatedBy(creator);
        event.setCreatedAt(ZonedDateTime.now());
        return repository.save(event);
    }

    @Transactional
    public void notify(EventType eventType, T entity) {
        var entityMetaInfo = resourceExtractor.getIdentification(entity);
        var resourceType = ResourceType.valueOfResource(entityMetaInfo.getLeft());
        var resourceId = entityMetaInfo.getRight();

        var eventDto = EventDto.<T>builder()
                .eventType(eventType)
                .resourceType(resourceType)
                .resourceId(resourceId)
                .requestUUID((String) httpServletRequest.getAttribute("requestUUID"))
                .entity(entity)
                .build();
        create(eventDto, currentUser());
        webSocketHandler.notifyClients(eventDto);
    }

  private User currentUser() {
        try {
            return userService.getCurrentUser();
        } catch (UsernameNotFoundException _ex) {
            return null;
        }
  }
}