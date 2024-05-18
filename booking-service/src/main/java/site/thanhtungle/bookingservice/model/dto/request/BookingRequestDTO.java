package site.thanhtungle.bookingservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.bookingservice.constant.enums.EBookingStatus;
import site.thanhtungle.bookingservice.model.entity.BookingItem;
import site.thanhtungle.bookingservice.model.entity.Customer;

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
    private EBookingStatus status;
    private Long userId;
    private Set<BookingItem> bookingItemIds;
    private Set<Customer> customer;
}
