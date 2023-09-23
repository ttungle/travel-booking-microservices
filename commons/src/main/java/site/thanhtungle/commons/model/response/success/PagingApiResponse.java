package site.thanhtungle.commons.model.response.success;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingApiResponse<T> extends BaseApiResponse<T> {
    private PageInfo pagination;

    public PagingApiResponse(int status, T data, PageInfo pageInfo) {
        super(status, data);
        this.pagination = pageInfo;
    }
}
