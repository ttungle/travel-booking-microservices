package site.thanhtungle.tourservice.model.criteria;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.tourservice.constant.enums.EFilterOperators;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourCriteria {

    @Min(1)
    private Integer page = 1;
    @Min(1)
    private Integer pageSize = 25;
    private String sort;
    private Map<String, Map<EFilterOperators, String>> filters;
}
