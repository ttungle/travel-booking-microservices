package site.thanhtungle.tourservice.model.dto.response.tour;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class BaseTourResponseDto {

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
    private String startLocation;
    private Instant startDate;
    private Instant createdAt;
    private Instant updatedAt;
    private String slug;
}
