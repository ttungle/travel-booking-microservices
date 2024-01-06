package site.thanhtungle.tourservice.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import site.thanhtungle.commons.util.CommonPageUtil;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class PageUtil {

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
