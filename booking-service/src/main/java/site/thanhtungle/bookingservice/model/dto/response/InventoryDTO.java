package site.thanhtungle.bookingservice.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class InventoryDTO {

    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant startDate;
    private Integer bookedQuantity;
    private Integer availableQuantity;
    private Integer totalQuantity;
    private Long tourId;
}
