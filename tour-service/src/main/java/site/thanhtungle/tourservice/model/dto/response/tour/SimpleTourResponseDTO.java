package site.thanhtungle.tourservice.model.dto.response.tour;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class SimpleTourResponseDTO {

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
    private String startLocation;
    private Instant startDate;
    private Instant createdAt;
    private Instant updatedAt;
    private String slug;
}
