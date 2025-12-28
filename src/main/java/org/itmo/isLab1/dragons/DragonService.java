package org.itmo.isLab1.dragons;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.itmo.isLab1.common.framework.CrudService;
import org.itmo.isLab1.dragons.dto.*;
import org.itmo.isLab1.dragons.mapper.DragonMapper;
import org.itmo.isLab1.dragons.policy.DragonPolicy;
import org.itmo.isLab1.events.EventService;
import org.itmo.isLab1.users.UserService;
import org.itmo.isLab1.common.errors.EntityDuplicateException;

import java.util.List;

@Service
public class DragonService
    extends CrudService<
        Dragon,
        DragonRepository,
        DragonMapper,
        DragonPolicy,
        DragonDto,
        DragonCreateDto,
        DragonUpdateDto> {

    public DragonService(
        DragonRepository repository,
        DragonMapper mapper,
        DragonPolicy policy,
        UserService userService,
        EventService<Dragon> eventService
    ) {
        super(repository, mapper, policy, userService, eventService);
    }

    /**
     * ОРИГИНАЛЬНАЯ ВЕРСИЯ (с гонкой данных)
     * Проблема: между проверкой existsByHead_IdAndIdNot и реальным сохранением объекта
     * может произойти вставка другого дракона с тем же headId из параллельного потока
     */
    @Override
    protected void checkUniqueness(Dragon obj){
        if(obj.getHead() == null) return;

        if(repository.existsByHead_IdAndIdNot(obj.getHead().getId(), obj.getId())){
            throw new EntityDuplicateException("headId is not uniq");
        }
    }

    /**
     * РЕШЕНИЕ 1: ИСПОЛЬЗОВАНИЕ УРОВНЯ ИЗОЛЯЦИИ SERIALIZABLE
     *
     * Как работает:
     * - SERIALIZABLE - самый высокий уровень изоляции транзакций
     * - Гарантирует полную сериализуемость выполнения транзакций
     * - Предотвращает phantom reads, non-repeatable reads и dirty reads
     * - База данных может откатить транзакцию при обнаружении конфликта сериализации
     *
     * Преимущества:
     * - Простота реализации - не требует изменения логики запросов
     * - Гарантирует консистентность данных на уровне СУБД
     * - Автоматически обнаруживает и разрешает конфликты
     *
     * Недостатки:
     * - Производительность: частые откаты транзакций при высокой конкуренции
     * - Может приводить к deadlock'ам
     * - Требует retry-логики для обработки serialization failures
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    protected void checkUniquenessSerialized(Dragon obj) {
        if(obj.getHead() == null) return;

        // При уровне SERIALIZABLE эта проверка атомарна относительно других транзакций
        // Если две транзакции одновременно проверяют один headId, одна из них будет откачена
        if(repository.existsByHead_IdAndIdNot(obj.getHead().getId(), obj.getId())){
            throw new EntityDuplicateException("headId is not uniq");
        }
        
        // Здесь может произойти SerializationFailureException, если обнаружен конфликт
        // Приложение должно перехватить это исключение и повторить операцию
    }

    /**
     * РЕШЕНИЕ 2: ИСПОЛЬЗОВАНИЕ ПЕССИМИСТИЧНОЙ БЛОКИРОВКИ (SELECT FOR UPDATE)
     *
     * Как работает:
     * - SELECT FOR UPDATE блокирует строки до окончания транзакции
     * - Другие транзакции будут ждать освобождения блокировки
     * - Гарантирует эксклюзивный доступ к проверяемым данным
     *
     * Преимущества:
     * - Предсказуемая производительность - нет откатов транзакций
     * - Явный контроль над блокировками
     * - Подходит для высоконагруженных систем
     *
     * Недостатки:
     * - Потенциальные deadlock'и если блокировки берутся в разном порядке
     * - Снижение параллелизма из-за ожидания блокировок
     * - Более сложная логика
     */
    @Transactional
    protected void checkUniquenessPessimistic(Dragon obj) {
        if(obj.getHead() == null) return;

        // Используем пессимистичную блокировку для чтения существующих драконов с данным headId
        // FOR UPDATE заблокирует найденные строки до конца транзакции
        List<Dragon> existingDragons = repository.findDragonsWithHeadIdExcludingDragonIdForUpdate(
            obj.getHead().getId(),
            obj.getId()
        );
        
        if (!existingDragons.isEmpty()) {
            throw new EntityDuplicateException("headId is not uniq");
        }
        
        // В этой точке мы гарантированно имеем эксклюзивный доступ
        // к проверке уникальности headId до конца транзакции
    }

    /**
     * АЛЬТЕРНАТИВНАЯ ВЕРСИЯ с нативным SQL
     * Использует нативный SQL для более точного контроля над блокировкой
     */
    @Transactional
    protected void checkUniquenessPessimisticNative(Dragon obj) {
        if(obj.getHead() == null) return;

        // Нативный SQL с FOR UPDATE для максимального контроля
        List<Dragon> existingDragons = repository.findDragonsWithHeadIdExcludingDragonIdNativeForUpdate(
            obj.getHead().getId(),
            obj.getId()
        );
        
        if (!existingDragons.isEmpty()) {
            throw new EntityDuplicateException("headId is not uniq");
        }
    }

    /**
     * ДЕМОНСТРАЦИОННЫЕ МЕТОДЫ для использования исправленных версий
     * В реальном приложении нужно заменить оригинальный checkUniqueness
     * одним из предложенных решений
     */
    
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Dragon create(DragonCreateDto createDto) {
        // Используем SERIALIZABLE изоляцию для всей операции создания
        Dragon dragon = mapper.createDtoToEntity(createDto);
        policy.validateCreatePermission(userService.getCurrentUser(), dragon);
        
        // Вызываем исправленную версию проверки уникальности
        checkUniquenessSerialized(dragon);
        
        Dragon savedDragon = repository.save(dragon);
        eventService.createEvent(savedDragon, userService.getCurrentUser().getId());
        return savedDragon;
    }
    
    /**
     * Альтернативная версия create с пессимистичной блокировкой
     */
    @Transactional
    public Dragon createWithPessimisticLocking(DragonCreateDto createDto) {
        Dragon dragon = mapper.createDtoToEntity(createDto);
        policy.validateCreatePermission(userService.getCurrentUser(), dragon);
        
        // Используем пессимистичную блокировку
        checkUniquenessPessimistic(dragon);
        
        Dragon savedDragon = repository.save(dragon);
        eventService.createEvent(savedDragon, userService.getCurrentUser().getId());
        return savedDragon;
    }
}