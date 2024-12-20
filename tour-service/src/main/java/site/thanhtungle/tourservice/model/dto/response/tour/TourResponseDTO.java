package site.thanhtungle.tourservice.model.dto.response.tour;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.commons.constant.enums.ETourStatus;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.SimpleTourCategoryResponseDTO;
import site.thanhtungle.tourservice.model.dto.response.tourexclude.SimpleTourExcludeResponseDTO;
import site.thanhtungle.tourservice.model.dto.response.tourfaq.SimpleTourFAQResponseDTO;
import site.thanhtungle.tourservice.model.dto.response.tourinclude.SimpleTourIncludeResponseDTO;
import site.thanhtungle.tourservice.model.entity.TourImage;
import site.thanhtungle.tourservice.model.entity.TourItinerary;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourResponseDTO implements Serializable {
    private Long id;
    private String name;
    private String duration;
    private Float ratingAverage;
    private Integer ratingQuantity;
    private BigDecimal price;
    private BigDecimal priceDiscount;
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
    private ETourStatus status;
    private String slug;
    private List<TourImage> images;
    private SimpleTourCategoryResponseDTO category;
    private List<TourItinerary> tourItineraries;
    private List<SimpleTourIncludeResponseDTO> tourIncludes;
    private List<SimpleTourExcludeResponseDTO> tourExcludes;
    private List<SimpleTourFAQResponseDTO> tourFAQs;
}
