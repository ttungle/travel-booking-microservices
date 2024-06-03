package site.thanhtungle.bookingservice.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.bookingservice.constant.enums.EBookingStatus;
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
    @NotNull(message = "Booking status cannot be null.")
    private EBookingStatus status;
    @NotNull(message = "userId cannot be null.")
    private Long userId;
    private Set<Long> bookingItemIds;
    @ListNotEmpty(message = "Booking must contain at lest 1 customerId.")
    private Set<Long> customerIds;
}
