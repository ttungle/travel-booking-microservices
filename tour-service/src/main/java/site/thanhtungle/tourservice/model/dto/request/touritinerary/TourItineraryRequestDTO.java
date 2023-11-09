package site.thanhtungle.tourservice.model.dto.request.touritinerary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourItineraryRequestDTO {

    private String title;
    private String description;
    private Instant time;
    private String location;
    private Long tourId;
}
