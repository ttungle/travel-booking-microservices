package site.thanhtungle.inventoryservice.service.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import site.thanhtungle.commons.constant.enums.EFilterOperators;
import site.thanhtungle.commons.predicate.FilterPredicate;

import java.util.List;
import java.util.Map;

@Service
public class AndFilterSpecification<T> extends FilterPredicate {

    public Specification<T> getAndFilterSpecification(Map<String, Map<EFilterOperators, String>> filters,
                                                      List<String> allowedFields) {
        return (root, query, cb) -> {
            if (filters == null || filters.isEmpty()) return null;
            List<Predicate> predicateList = getPredicateList(filters, allowedFields, cb, root);
            return cb.and(predicateList.toArray(Predicate[]::new));
        };
    }
}
