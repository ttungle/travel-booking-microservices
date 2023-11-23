package site.thanhtungle.tourservice.model.dto.response.tourinclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTourIncludeResponseDTO {

    private Long id;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
}