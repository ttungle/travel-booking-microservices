package site.thanhtungle.paymentservice.model.dto;

import lombok.Getter;
import lombok.Setter;
import site.thanhtungle.commons.constant.enums.EBookingStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class Booking {

    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private BigDecimal subtotal;
    private BigDecimal grandTotal;
    private String coupon;
    private boolean paid;
    private String note;
    private EBookingStatus status;
    private String userId;
}
