package site.thanhtungle.bookingservice.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
@Entity
@Table(name = "booking")
public class Booking extends BaseEntity {

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "grand_total")
    private BigDecimal grandTotal;

    @Column(name = "coupon")
    private String coupon;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private EBookingStatus status;

    @Column(name = "user_id")
    private String userId;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "booking",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<BookingItem> bookingItems;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "booking",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    private Set<Customer> customers;
}
