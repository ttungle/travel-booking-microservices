package site.thanhtungle.tourservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.thanhtungle.tourservice.model.entity.TourFAQ;

@Repository
public interface TourFAQRepository extends JpaRepository<TourFAQ, Long> {
}
