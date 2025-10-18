package org.itmo.isLab1.common.framework;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrudSpecification {
    
    public static <T> Specification<T> buildSpecification(Map<String, String> filters) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                String fieldName = entry.getKey();
                String value = entry.getValue();
                
                try {
                    // Проверяем, существует ли поле
                    var field = root.get(fieldName);
                    
                    // Получаем тип поля
                    Class<?> fieldType = field.getJavaType();
                    
                    // Фильтруем только строковые поля
                    if (String.class.isAssignableFrom(fieldType)) {
                        predicates.add(criteriaBuilder.equal(field, value));
                    } else if (Enum.class.isAssignableFrom(fieldType)) {
                        // Поддержка Enum полей
                        try {
                            @SuppressWarnings("unchecked")
                            Enum<?> enumValue = Enum.valueOf((Class<Enum>) fieldType, value);
                            predicates.add(criteriaBuilder.equal(field, enumValue));
                        } catch (IllegalArgumentException ignored) {
                            // Если значение не является допустимым для enum, пропускаем
                        }
                    }
                    
                } catch (IllegalArgumentException e) {
                    // Поле не существует, пропускаем
                }
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}