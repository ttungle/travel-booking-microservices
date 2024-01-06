package site.thanhtungle.tourservice.model.criteria;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchTourCriteria {

    private String q;

    @Min(1)
    private Integer page = 1;

    @Min(1)
    private Integer pageSize = 25;

    private String sort;

    private Long categoryId;

    private String startLocation;

    private String priceDiscount;

    private String ratingAverage;

    private String startDate;

    private String[] fields = { "name", "startLocation" };
}
