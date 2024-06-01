package site.thanhtungle.inventoryservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Start date cannot be null.")
    private Instant startDate;

    @Column(name = "booked_quantity")
    @Min(0)
    private Integer bookedQuantity;

    @Column(name = "available_quantity")
    @Min(0)
    private Integer availableQuantity;

    @Column(name = "total_quantity")
    @Min(0)
    private Integer totalQuantity;

    @Column(name = "tour_id")
    @NotNull(message = "Tour id cannot be null")
    private Long tourId;
}
