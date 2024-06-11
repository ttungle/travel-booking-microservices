package site.thanhtungle.tourservice.model.dto.request.tour;

import lombok.Getter;
import lombok.Setter;
import site.thanhtungle.commons.constant.enums.ETourStatus;

@Getter
@Setter
public class TourStatusRequestDTO {
    private ETourStatus status;
}
