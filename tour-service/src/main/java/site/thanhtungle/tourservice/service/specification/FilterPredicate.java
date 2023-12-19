package site.thanhtungle.tourservice.service.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;
import site.thanhtungle.tourservice.constant.enums.EFilterOperators;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FilterPredicate {

    public Predicate getPredicate(EFilterOperators operator, String value, CriteriaBuilder cb, Path expression) {
        Predicate predicate = null;
        switch (operator) {
            case EQ -> {
                predicate = cb.equal(expression, value);
            }
            case NE -> {
                predicate = cb.notEqual(expression,value);
            }
            case IN -> {
                predicate = cb.in(expression).value(value);
            }
            case GT -> {
                predicate = cb.greaterThan(expression, value);
            }
            case LT -> {
                predicate = cb.lessThan(expression, value);
            }
            case GTE -> {
                predicate = cb.greaterThanOrEqualTo(expression, value);
            }
            case LTE -> {
                predicate = cb.lessThanOrEqualTo(expression, value);
            }
            case BETWEEN -> {
                String[] splitValue= value.split(",");
                predicate = cb.between(expression, splitValue[0], splitValue[1]);
            }
            default -> throw new IllegalStateException("Invalid operator.");
        }
        return predicate;
    }

    public List<Predicate> getPredicateList(
            Map<String, Map<EFilterOperators, String>> filters,
            List<String> allowedFilterFields, CriteriaBuilder cb,
            Root root
    ) {
            List<Predicate> predicateList = new ArrayList<>();
            filters.forEach((field, OperatorValuePairs) -> {
                // Prevent fields which are not in allowed filter list
                if (!allowedFilterFields.contains(field)) throw new RuntimeException(field + " cannot be filtered.");

                OperatorValuePairs.forEach((operator, value) -> {
                    String decodedValue = URLDecoder.decode(value, StandardCharsets.UTF_8);
                    Predicate predicate = getPredicate(operator, decodedValue, cb, root.get(field));
                    predicateList.add(predicate);
                });
            });
            return predicateList;
    }
}
