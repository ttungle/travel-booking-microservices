package site.thanhtungle.commons.predicate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import site.thanhtungle.commons.constant.enums.EFilterOperators;
import site.thanhtungle.commons.validation.DateValidator;
import site.thanhtungle.commons.validation.DateValidatorImpl;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class FilterPredicate {

    private Predicate getPredicate(String field, EFilterOperators operator, String value, CriteriaBuilder cb, Path<?> expression) {
        Predicate predicate = null;
        switch (operator) {
            case EQ -> {
                boolean isDateField = field.toLowerCase().contains("date");
                if (isDateField) predicate = cb.equal(expression, stringToInstant(value));
                else predicate = cb.equal(expression, value);
            }
            case NE -> {
                boolean isDateField = field.toLowerCase().contains("date");
                if (isDateField) predicate = cb.notEqual(expression, stringToInstant(value));
                else predicate = cb.notEqual(expression, value);
            }
            case IN -> {
                predicate = cb.in(expression).in(value);
            }
            case GT -> {
                predicate = cb.greaterThan(expression.as(String.class), value);
            }
            case LT -> {
                predicate = cb.lessThan(expression.as(String.class), value);
            }
            case GTE -> {
                predicate = cb.greaterThanOrEqualTo(expression.as(String.class), value);
            }
            case LTE -> {
                predicate = cb.lessThanOrEqualTo(expression.as(String.class), value);
            }
            case BETWEEN -> {
                String[] splitValue = value.split(",");
                predicate = cb.between(expression.as(String.class), splitValue[0], splitValue[1]);
            }
            default -> {
                log.error("Invalid operator");
                throw new IllegalArgumentException(operator + " is not a valid operator");
            }
        }
        return predicate;
    }

    public List<Predicate> getPredicateList(Map<String, Map<EFilterOperators, String>> filters,
                                            List<String> allowedFields, CriteriaBuilder cb, Root root) {
        List<Predicate> predicateList = new ArrayList<>();
        filters.forEach((field, operatorValuePairs) -> {
            if (!allowedFields.contains(field))
                throw new RuntimeException(field + " cannot be filtered or field does not exist.");

            operatorValuePairs.forEach((operator, value) -> {
                String decodedValue = URLDecoder.decode(value, StandardCharsets.UTF_8);
                Predicate predicate = getPredicate(field, operator, decodedValue, cb, root.get(field));
                predicateList.add(predicate);
            });
        });
        return predicateList;
    }

    private Instant stringToInstant(String dateString) {
        DateValidator dateValidator = new DateValidatorImpl();
        if (!dateValidator.isValid(dateString))
            throw new IllegalArgumentException("Invalid date format, date format should be YYYY-MM-DDTHH:MM:SSZ+HH:MM");
        return Instant.parse(dateString);
    }
}