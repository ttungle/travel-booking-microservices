package site.thanhtungle.commons.model.response.success;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PagingApiResponse<T> extends BaseApiResponse<T> implements Serializable {
    private PageInfo pagination;

    public PagingApiResponse(int status, T data, PageInfo pageInfo) {
        super(status, data);
        this.pagination = pageInfo;
    }
}
