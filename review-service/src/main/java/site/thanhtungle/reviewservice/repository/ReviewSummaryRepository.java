package site.thanhtungle.reviewservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.thanhtungle.reviewservice.model.entity.ReviewSummary;

public interface ReviewSummaryRepository extends JpaRepository<ReviewSummary, Long> {
}
