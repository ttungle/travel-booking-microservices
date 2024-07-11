package site.thanhtungle.tourservice.model.dto.response.booking;

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
public class BookingItem {

    private Integer adultQuantity;
    private Integer childQuantity;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private EBookingItemStatus status;
    private String note;
    private Instant startDate;
    private String userId;
    private Long tourId;
    private Long inventoryId;
}
