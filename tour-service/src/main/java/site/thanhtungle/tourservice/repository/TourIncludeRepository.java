package site.thanhtungle.tourservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.thanhtungle.tourservice.model.entity.TourInclude;

@Repository
public interface TourIncludeRepository extends JpaRepository<TourInclude, Long> {
}
