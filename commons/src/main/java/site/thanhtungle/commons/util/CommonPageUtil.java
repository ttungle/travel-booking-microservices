package site.thanhtungle.commons.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
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

    public PageRequest getPageRequest(Integer page, Integer pageSize, String sort) {
        if (page == null || page < 1) throw new InvalidParameterException("Invalid page.");
        if (pageSize == null || pageSize < 0) throw new InvalidParameterException("Invalid page size.");

        PageRequest pageRequest;
        if (sort != null) {
            Sort sortRequest = CommonPageUtil.getSort(sort);
            if (sortRequest == null) throw new InvalidParameterException("Invalid sort value.");
            pageRequest = PageRequest.of(page - 1, pageSize, sortRequest);
        } else {
            pageRequest = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        }

        return pageRequest;
    }

    public List<String> getStringSort(String sort) {
        if (sort == null) return null;
        int sortParameterCount = StringUtils.countOccurrencesOf(sort, ",");
        if (sortParameterCount > 1) throw new InvalidParameterException("Cannot sort more than 1 field.");

        if (!sort.contains(":")) {
            return Arrays.asList(sort, "asc");
        }

        String[] splitFields = sort.split(":");
        return switch (splitFields[1]) {
            case "asc" -> Arrays.asList(splitFields[0], "asc");
            case "desc" -> Arrays.asList(splitFields[0], "desc");
            default -> throw new InvalidParameterException(
                    "Invalid sort direction. Sorting direction should be 'asc' or 'desc'.");
        };
    }
}
