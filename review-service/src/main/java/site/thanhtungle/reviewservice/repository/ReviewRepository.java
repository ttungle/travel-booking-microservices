package site.thanhtungle.reviewservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.thanhtungle.reviewservice.model.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.tourId = :tourId")
    Float getAverageRating(Long tourId);
}
