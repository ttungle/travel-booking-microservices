package site.thanhtungle.bookingservice.model.dto.request.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.commons.constant.enums.EBookingStatus;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingUpdateRequestDTO {

    private BigDecimal subtotal;
    private BigDecimal grandTotal;
    private String coupon;
    private boolean paid;
    private String note;
    private EBookingStatus status;
    private Set<Long> bookingItemIds;
    private Set<Long> customerIds;
}
