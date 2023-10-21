package site.thanhtungle.commons.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Sort;

@UtilityClass
public class CommonPageUtil {

    public Sort getSort(String sort) {
        int sortParameterCount = StringUtils.countOccurrencesOf(sort, ",");
        if (sortParameterCount >= 2) throw new InvalidParameterException("Cannot sort more than 2 fields.");

        List<Sort.Order> orderList;
        if (!sort.contains(":")) {
            orderList = Arrays.stream(sort.split(","))
                    .map(field -> new Sort.Order(Sort.Direction.ASC, field))
                    .toList();
            return Sort.by(orderList);
        }

        String[] sortElements = sort.split(",");
        orderList = Arrays.stream(sortElements).map(element -> {
            if (!element.contains(":")) return new Sort.Order(Sort.Direction.ASC, element);

            String[] splitFields = element.split(":");
            return switch (splitFields[1]) {
                case "asc" -> new Sort.Order(Sort.Direction.ASC, splitFields[0]);
                case "desc" -> new Sort.Order(Sort.Direction.DESC, splitFields[0]);
                default -> throw new InvalidParameterException(
                        "Invalid sort direction. Sorting direction should be 'asc' or 'desc'.");
            };
        }).toList();
        return orderList.isEmpty() ? null : Sort.by(orderList);
    }
}
