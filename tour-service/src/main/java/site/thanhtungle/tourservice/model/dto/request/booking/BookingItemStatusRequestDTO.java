package site.thanhtungle.tourservice.model.dto.request.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.thanhtungle.commons.constant.enums.EBookingItemStatus;

@Getter
@Setter
@AllArgsConstructor
public class BookingItemStatusRequestDTO {

    private EBookingItemStatus status;
}
