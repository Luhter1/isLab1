package org.itmo.isLab1.common.framework;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrudSpecification {

    private static final Logger logger = LoggerFactory.getLogger(CrudSpecification.class);

    public static <T> Specification<T> buildSpecification(Map<String, String> filters) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            query.distinct(true);

            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, String> entry : filters.entrySet()) {
                String fieldName = entry.getKey();
                String value = entry.getValue();

                // Пропускаем пустые значения фильтров
                if (value == null || value.trim().isEmpty()) {
                    continue;
                }

                try {
                    Path<?> fieldPath = parsePath(root, fieldName);
                    Class<?> fieldType = fieldPath.getJavaType();

                    if (String.class.isAssignableFrom(fieldType)) {
                        predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(fieldPath.as(String.class)), value));
                    } else if (Enum.class.isAssignableFrom(fieldType)) {
                        try {
                            @SuppressWarnings("unchecked")
                            Enum<?> enumValue = Enum.valueOf((Class<Enum>) fieldType, value.toUpperCase());
                            predicates.add(criteriaBuilder.equal(fieldPath, enumValue));
                        } catch (IllegalArgumentException e) {
                            logger.warn("Invalid enum value '{}' for field '{}'", value, fieldName);
                        }
                    } else if (Number.class.isAssignableFrom(fieldType)) {
                        try {
                            // Простое сравнение на равенство для чисел
                            predicates.add(criteriaBuilder.equal(fieldPath, value));
                        } catch (Exception e) {
                             logger.warn("Invalid number value '{}' for field '{}'", value, fieldName);
                        }
                    }

                } catch (IllegalArgumentException e) {
                    logger.warn("Could not resolve path for filter: '{}'. Field might not exist.", fieldName);
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    private static <T> Path<?> parsePath(Root<T> root, String fieldName) {
        Path<?> path = root;
        for (String part : fieldName.split("\\.")) {
            path = path.get(part);
        }
        return path;
    }
}