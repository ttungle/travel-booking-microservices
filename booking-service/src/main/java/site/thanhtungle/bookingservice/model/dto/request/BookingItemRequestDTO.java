package site.thanhtungle.bookingservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingItemRequestDTO {

    private Integer adultQuantity;
    private Integer childQuantity;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String note;
    private Instant startDate;
    private Long tourId;
    private Long bookingId;
}
