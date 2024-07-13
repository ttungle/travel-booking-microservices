package site.thanhtungle.bookingservice.model.dto.request.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.bookingservice.validation.ListNotEmpty;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    private BigDecimal subtotal;
    private BigDecimal grandTotal;
    private String coupon;
    private boolean paid;
    private String note;
    private Set<Long> bookingItemIds;
    @ListNotEmpty(message = "Booking must contain at lest 1 customerId.")
    private Set<Long> customerIds;
}
