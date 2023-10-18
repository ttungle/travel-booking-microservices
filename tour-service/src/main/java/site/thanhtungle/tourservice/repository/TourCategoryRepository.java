package site.thanhtungle.tourservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.thanhtungle.tourservice.model.entity.TourCategory;

@Repository
public interface TourCategoryRepository extends JpaRepository<TourCategory, Long> {
}
