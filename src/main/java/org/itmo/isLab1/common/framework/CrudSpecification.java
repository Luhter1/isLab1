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
                    String[] parts = value.split(":", 2);
                    String operator = parts.length > 1 ? parts[0] : "eq";
                    String operand  = parts.length > 1 ? parts[1] : parts[0];

                    if (String.class.isAssignableFrom(fieldType)) {
                        switch (operator) {
                            case "starts":
                                predicates.add(criteriaBuilder.like(
                                    criteriaBuilder.lower(fieldPath.as(String.class)),
                                    operand.toLowerCase() + "%"
                                ));
                                break;

                            case "contains":
                                predicates.add(criteriaBuilder.like(
                                    criteriaBuilder.lower(fieldPath.as(String.class)),
                                    "%" + operand.toLowerCase() + "%"
                                ));
                                break;

                            case "eq":
                            default:
                                predicates.add(criteriaBuilder.equal(
                                    criteriaBuilder.lower(fieldPath.as(String.class)),
                                    operand.toLowerCase()
                                ));
                                break;
                        }
                    } else if (Enum.class.isAssignableFrom(fieldType)) {
                        try {
                            @SuppressWarnings("unchecked")
                            Enum<?> enumValue = Enum.valueOf((Class<Enum>) fieldType, value.toUpperCase());
                            predicates.add(criteriaBuilder.equal(fieldPath, enumValue));
                        } catch (IllegalArgumentException e) {
                            logger.warn("Invalid enum value '{}' for field '{}'", value, fieldName);
                            predicates.add(criteriaBuilder.disjunction());
                        }
                    }

                } catch (IllegalArgumentException e) {
                    logger.warn("Could not resolve path for filter: '{}'. Field might not exist.", fieldName);
                    predicates.add(criteriaBuilder.disjunction());
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