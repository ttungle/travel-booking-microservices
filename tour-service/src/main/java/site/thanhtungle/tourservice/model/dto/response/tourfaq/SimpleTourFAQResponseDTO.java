package site.thanhtungle.tourservice.model.dto.response.tourfaq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTourFAQResponseDTO {

    private Long id;
    private String question;
    private String answer;
    private Instant createdAt;
    private Instant updatedAt;
}
