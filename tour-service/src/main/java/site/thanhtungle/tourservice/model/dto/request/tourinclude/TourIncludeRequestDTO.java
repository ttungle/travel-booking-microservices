package site.thanhtungle.tourservice.model.dto.request.tourinclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourIncludeRequestDTO {

    private String content;
    private List<Long> tourIds;
}
