package site.thanhtungle.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.thanhtungle.bookingservice.model.entity.BookingItem;

@Repository
public interface BookingItemRepository extends JpaRepository<BookingItem, Long> {
}
