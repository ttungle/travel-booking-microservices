package site.thanhtungle.reviewservice.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSummaryRequestDTO {

    @Min(value = 0, message = "averageRating cannot be less than 0.")
    private Float averageRating;
    @NotNull(message = "tourId cannot be null.")
    private Long tourId;
}
