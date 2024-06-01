package site.thanhtungle.inventoryservice.model.criteria;

import lombok.Getter;
import lombok.Setter;
import site.thanhtungle.commons.constant.enums.EFilterOperators;
import site.thanhtungle.commons.model.criteria.BaseCriteria;

import java.util.Map;

@Getter
@Setter
public class InventoryCriteria extends BaseCriteria {

    // Example filters: {filters: {fieldName: {operator: value}}}
    private Map<String, Map<EFilterOperators, String>> filters;
}
