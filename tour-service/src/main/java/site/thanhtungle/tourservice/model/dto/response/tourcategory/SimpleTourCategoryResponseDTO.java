package site.thanhtungle.tourservice.model.dto.response.tourcategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTourCategoryResponseDTO {

    private Long id;
    private String name;
    private String description;
}
