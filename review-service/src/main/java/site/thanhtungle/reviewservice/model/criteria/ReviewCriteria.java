package site.thanhtungle.reviewservice.model.criteria;

import lombok.Getter;
import lombok.Setter;
import site.thanhtungle.commons.constant.enums.EFilterOperators;

import java.util.Map;

@Getter
@Setter
public class ReviewCriteria extends BaseCriteria {

    private Map<String, Map<EFilterOperators, String>> filters;
}
