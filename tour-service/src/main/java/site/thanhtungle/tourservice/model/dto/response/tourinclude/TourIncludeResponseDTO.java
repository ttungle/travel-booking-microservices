package site.thanhtungle.tourservice.model.dto.response.tourinclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.tourservice.model.dto.response.tour.TourResponseDTO;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourIncludeResponseDTO {

    private Long id;
    private String content;
    private List<TourResponseDTO> tours;
    private Instant createdAt;
    private Instant updatedAt;
}
