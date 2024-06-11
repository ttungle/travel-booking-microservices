package site.thanhtungle.bookingservice.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import site.thanhtungle.commons.constant.enums.EBookingItemStatus;

@Getter
@Setter
public class BookingItemStatusRequestDTO {

    @NotNull(message = "The status cannot be null.")
    private EBookingItemStatus status;
}
