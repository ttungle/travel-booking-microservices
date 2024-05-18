package site.thanhtungle.bookingservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemRequestDTO;
import site.thanhtungle.bookingservice.model.entity.BookingItem;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;

import java.util.List;

@Transactional
public interface BookingItemService {


    BookingItem createBookingItem(BookingItemRequestDTO bookingItemRequestDTO);

    BookingItem updateBookingItem(Long bookingItemId, BookingItemRequestDTO bookingItemRequestDTO);

    @Transactional(readOnly = true)
    BookingItem getBookingItem(Long bookingItemId);

    @Transactional(readOnly = true)
    PagingApiResponse<List<BookingItem>> getAllBookingItems(BaseCriteria bookingItemCriteria);

    void deleteBookingItem(Long bookingItemId);
}
