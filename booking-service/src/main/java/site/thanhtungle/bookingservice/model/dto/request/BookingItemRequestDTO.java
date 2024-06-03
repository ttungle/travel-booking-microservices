package site.thanhtungle.bookingservice.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @Min(value = 0, message = "adultQuantity should greater than or equal 0.")
    private Integer adultQuantity;
    @Min(value = 0, message = "childQuantity should greater than or equal 0.")
    private Integer childQuantity;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String note;
    @NotNull(message = "startDate cannot be null.")
    private Instant startDate;
    @NotNull(message = "tourId cannot be null.")
    private Long tourId;
    @NotNull(message = "bookingId cannot be null.")
    private Long bookingId;
}
