package site.thanhtungle.reviewservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review_summary")
public class ReviewSummary extends BaseEntity {

    @Column(name = "average_rating")
    private Float averageRating;

    @Column(name = "tour_id")
    private Long tourId;
}
