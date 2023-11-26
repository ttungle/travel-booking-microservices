package site.thanhtungle.tourservice.model.dto.response.tourfaq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.tourservice.model.dto.response.tour.SimpleTourResponseDTO;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourFAQResponseDTO {

    private Long id;
    private String question;
    private String answer;
    private Instant createdAt;
    private Instant updatedAt;
    private List<SimpleTourResponseDTO> tours;
}
