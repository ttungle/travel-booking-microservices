package site.thanhtungle.tourservice.model.dto.response.tour;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.SimpleTourCategoryResponseDTO;
import site.thanhtungle.tourservice.model.dto.response.tourimage.SimpleTourImageResponseDTO;
import site.thanhtungle.tourservice.model.dto.response.touritinerary.SimpleTourItineraryResponseDTO;
import site.thanhtungle.tourservice.model.entity.TourExclude;
import site.thanhtungle.tourservice.model.entity.TourFAQ;
import site.thanhtungle.tourservice.model.entity.TourInclude;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourResponseDTO {
    private Long id;
    private String name;
    private String duration;
    private Float ratingAverage;
    private Integer ratingQuantity;
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
    private Instant createdAt;
    private Instant updatedAt;
    private String slug;
    private List<SimpleTourImageResponseDTO> images;
    private SimpleTourCategoryResponseDTO category;
    private List<SimpleTourItineraryResponseDTO> tourItineraries;
    private List<TourInclude> tourIncludes;
    private List<TourExclude> tourExcludes;
    private List<TourFAQ> tourFAQs;
}
