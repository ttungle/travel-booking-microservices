package site.thanhtungle.bookingservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
@Entity
@Table(name = "booking_item")
public class BookingItem extends BaseEntity {

    @Column(name = "adult_quantity")
    private Integer adultQuantity;

    @Column(name = "child_quantity")
    private Integer childQuantity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    @Column(name = "status")
    private EBookingItemStatus status;

    @Column(name = "note")
    private String note;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "tour_id")
    private Long tourId;

    @Column(name = "inventory_id")
    private Long inventoryId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST})
    private Booking booking;
}
