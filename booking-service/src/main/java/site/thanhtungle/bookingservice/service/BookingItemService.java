package site.thanhtungle.bookingservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemRequestDTO;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemStatusRequestDTO;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemUpdateRequestDTO;
import site.thanhtungle.bookingservice.model.entity.BookingItem;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;

import java.util.List;

@Transactional
public interface BookingItemService {


    BookingItem createBookingItem(String userId, BookingItemRequestDTO bookingItemRequestDTO);

    BookingItem updateBookingItem(Long bookingItemId, BookingItemUpdateRequestDTO bookingItemRequestDTO);

    List<BookingItem> batchUpdateBookingItemStatus(Long tourId, BookingItemStatusRequestDTO bookingItemStatusRequestDTO);

    @Transactional(readOnly = true)
    BookingItem getBookingItem(Long bookingItemId);

    @Transactional(readOnly = true)
    PagingApiResponse<List<BookingItem>> getAllBookingItems(BaseCriteria bookingItemCriteria);

    boolean checkBookingItemExistByTourId(Long tourId);

    void deleteBookingItem(Long bookingItemId);
}
