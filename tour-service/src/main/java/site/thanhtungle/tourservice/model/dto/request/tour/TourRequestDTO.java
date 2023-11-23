package site.thanhtungle.tourservice.model.dto.request.tour;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.tourservice.model.entity.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourRequestDTO {

    private Long id;
    private String name;
    private String duration;
    private Float price;
    private Float priceDiscount;
    private String tourType;
    private Integer groupSize;
    private String summary;
    private String description;
    private String startLocation;
    private Instant startDate;
    private String slug;
    private Long categoryId;
    private List<Long> tourItineraryIds;
    private List<Long> tourIncludeIds;
    private List<Long> tourExcludeIs;
    private List<TourFAQ> tourFAQs;
}
