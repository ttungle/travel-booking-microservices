package site.thanhtungle.bookingservice.model.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.commons.constant.enums.EBookingItemStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingItemUpdateRequestDTO {

    @Min(value = 0, message = "adultQuantity should greater than or equal 0.")
    private Integer adultQuantity;
    @Min(value = 0, message = "childQuantity should greater than or equal 0.")
    private Integer childQuantity;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private EBookingItemStatus status;
    private String note;
    private Instant startDate;
    private Long tourId;
    private Long inventoryId;
    private Long bookingId;
}
