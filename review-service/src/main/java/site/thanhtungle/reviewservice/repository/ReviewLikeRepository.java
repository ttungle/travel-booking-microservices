package site.thanhtungle.reviewservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.thanhtungle.reviewservice.model.entity.ReviewLike;

import java.util.Optional;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    Optional<ReviewLike> findReviewLikeByUserIdAndReviewId(String userId, Long reviewId);
}
