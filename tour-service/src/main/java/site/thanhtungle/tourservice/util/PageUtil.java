package site.thanhtungle.tourservice.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import site.thanhtungle.commons.util.CommonPageUtil;

import java.security.InvalidParameterException;

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
}
