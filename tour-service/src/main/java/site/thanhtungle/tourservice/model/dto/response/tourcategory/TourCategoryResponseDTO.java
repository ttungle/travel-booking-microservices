package site.thanhtungle.tourservice.model.dto.response.tourcategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.tourservice.model.dto.response.tour.SimpleTourResponseDTO;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourCategoryResponseDTO implements Serializable {

    private Long id;
    private String name;
    private String description;
    private List<SimpleTourResponseDTO> tours;
}
