package site.thanhtungle.tourservice.service.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import site.thanhtungle.tourservice.constant.enums.EFilterOperators;

import java.util.List;
import java.util.Map;

@Service
public class AndFilterSpecification<T> extends FilterPredicate {

    public Specification<T> getAndFilterSpecification(Map<String, Map<EFilterOperators, String>> filters,
                                                List<String> allowedFilterFields) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = getPredicateList(filters, allowedFilterFields, cb, root);
            return cb.and(predicateList.toArray(Predicate[]::new));
        };
    }
}
