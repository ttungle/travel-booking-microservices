package site.thanhtungle.tourservice.model.dto.response.tourcategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTourCategoryResponseDTO implements Serializable {

    private Long id;
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
}
