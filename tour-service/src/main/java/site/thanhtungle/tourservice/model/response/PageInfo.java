package site.thanhtungle.tourservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageInfo {

    private Integer page;
    private Integer pageSize;
    private Long totalCount;
    private Integer totalPages;

    public PageInfo(Integer page, Integer pageSize, Long totalCount) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        if (totalCount % pageSize > 0) {
            this.totalPages = (int) (totalCount / pageSize) + 1;
        } else {
            this.totalPages = (int) (totalCount / pageSize);
        }
    }
}
