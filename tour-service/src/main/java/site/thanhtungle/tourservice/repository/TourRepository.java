package site.thanhtungle.tourservice.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.thanhtungle.tourservice.model.entity.Tour;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourRepository extends SearchRepository<Tour, Long>, JpaSpecificationExecutor<Tour> {

    @Query("SELECT t FROM Tour t LEFT JOIN FETCH t.images WHERE t.id = :tourId")
    Optional<Tour> findByIdWithLazyImages(@Param("tourId") Long tourId);

    List<Tour> findTourByIdIn(List<Long> tourIdList);
}
