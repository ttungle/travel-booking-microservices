package site.thanhtungle.tourservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.thanhtungle.tourservice.model.entity.Tour;

import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    @Query("SELECT t FROM Tour t LEFT JOIN FETCH t.images WHERE t.id = :tourId")
    Optional<Tour> findByIdWithLazyImages(@Param("tourId") Long tourId);
}
