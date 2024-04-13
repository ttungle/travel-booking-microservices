package site.thanhtungle.tourservice.model.dto.response.touritinerary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.tourservice.model.dto.response.tour.TourResponseDTO;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourItineraryResponseDTO implements Serializable {

    private Long id;
    private String title;
    private String description;
    private Instant time;
    private String location;
    private TourResponseDTO tour;
}
