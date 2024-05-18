package site.thanhtungle.bookingservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemRequestDTO;
import site.thanhtungle.bookingservice.model.entity.BookingItem;
import site.thanhtungle.bookingservice.service.BookingItemService;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingItemServiceImpl implements BookingItemService {
    @Override
    public BookingItem createBookingItem(BookingItemRequestDTO bookingItemRequestDTO) {
        return null;
    }

    @Override
    public BookingItem updateBookingItem(Long bookingItemId, BookingItemRequestDTO bookingItemRequestDTO) {
        return null;
    }

    @Override
    public BookingItem getBookingItem(Long bookingItemId) {
        return null;
    }

    @Override
    public PagingApiResponse<List<BookingItem>> getAllBookingItems(BaseCriteria bookingItemCriteria) {
        return null;
    }

    @Override
    public void deleteBookingItem(Long bookingItemId) {

    }
}
