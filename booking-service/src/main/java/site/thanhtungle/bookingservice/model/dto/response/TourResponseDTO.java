package site.thanhtungle.bookingservice.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import site.thanhtungle.commons.constant.enums.ETourStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class TourResponseDTO {
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
}
