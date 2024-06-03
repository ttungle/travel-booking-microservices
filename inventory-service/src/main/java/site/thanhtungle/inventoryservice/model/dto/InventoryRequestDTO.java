package site.thanhtungle.inventoryservice.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class InventoryRequestDTO {

    private Long id;
    @NotNull(message = "startDate cannot be null.")
    private Instant startDate;
    @Min(value = 0, message = "bookedQuantity should greater than or equal 0.")
    private Integer bookedQuantity;
    @Min(value = 0, message = "availableQuantity should greater than or equal 0.")
    private Integer totalQuantity;
    @NotNull(message = "tourId cannot be null")
    private Long tourId;
}
