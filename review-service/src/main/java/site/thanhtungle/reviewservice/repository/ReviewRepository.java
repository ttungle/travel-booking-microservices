package site.thanhtungle.reviewservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.thanhtungle.reviewservice.model.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
