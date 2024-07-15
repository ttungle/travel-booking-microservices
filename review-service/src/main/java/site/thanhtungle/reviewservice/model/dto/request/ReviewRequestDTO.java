package site.thanhtungle.reviewservice.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDTO {

    private String author;
    private String avatar;
    @NotNull(message = "Review content cannot be null.")
    @NotEmpty(message = "Review content cannot be empty.")
    private String content;
    private String language;
    @Min(value = 0, message = "rating cannot be less than 0.")
    private Integer rating;
    @NotNull(message = "tourId cannot be null.")
    private Long tourId;
}
