package site.thanhtungle.inventoryservice.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookedQuantityRequestDTO {
    @NotNull(message = "quantity cannot be null.")
    @Min(value = 1, message = "quantity should greater than 0.")
    private Integer quantity;
}
