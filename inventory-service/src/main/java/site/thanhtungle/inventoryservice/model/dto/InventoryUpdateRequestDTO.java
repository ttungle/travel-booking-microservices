package site.thanhtungle.inventoryservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class InventoryUpdateRequestDTO {
    private Long id;
    private Instant startDate;
    private Integer bookedQuantity;
    private Integer totalQuantity;
    private Long tourId;
}
