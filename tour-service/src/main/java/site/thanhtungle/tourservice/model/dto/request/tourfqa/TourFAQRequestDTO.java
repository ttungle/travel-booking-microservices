package site.thanhtungle.tourservice.model.dto.request.tourfqa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourFAQRequestDTO {

    private String question;
    private String answer;
    private List<Long> tourIds;
}
