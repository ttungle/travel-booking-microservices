package site.thanhtungle.tourservice.model.dto.response.tourinclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.tourservice.model.dto.response.tour.SimpleTourResponseDTO;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourIncludeResponseDTO implements Serializable {

    private Long id;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
    private List<SimpleTourResponseDTO> tours;
}
