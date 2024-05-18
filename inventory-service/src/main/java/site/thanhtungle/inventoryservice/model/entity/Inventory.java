package site.thanhtungle.inventoryservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory extends BaseEntity {

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "booked_quantity")
    private Integer bookedQuantity;

    @Column(name = "available_quantity")
    private Integer availableQuantity;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "tour_id")
    private Long tourId;
}
