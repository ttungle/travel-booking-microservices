package site.thanhtungle.commons.model.criteria;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseCriteria {

    @Min(1)
    private Integer page = 1;
    @Min(1)
    private Integer pageSize = 25;
    private String sort;
}
