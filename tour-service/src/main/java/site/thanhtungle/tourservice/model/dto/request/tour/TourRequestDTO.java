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
    private String coverImage;
    private String video;
    private String startLocation;
    private Instant startDate;
    private String slug;
    private List<TourImage> images;
    private TourCategory category;
    private List<TourItinerary> tourItinerary;
    private List<TourInclude> tourIncludes;
    private List<TourExclude> tourExcludes;
    private List<TourFAQ> tourFAQs;
}
