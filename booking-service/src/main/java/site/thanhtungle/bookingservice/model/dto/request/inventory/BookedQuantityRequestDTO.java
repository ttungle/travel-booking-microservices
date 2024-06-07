package site.thanhtungle.bookingservice.model.dto.request.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookedQuantityRequestDTO {
    private Integer quantity;
}
