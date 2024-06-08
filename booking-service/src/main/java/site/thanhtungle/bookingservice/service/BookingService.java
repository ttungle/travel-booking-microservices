package site.thanhtungle.bookingservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.booking.BookingRequestDTO;
import site.thanhtungle.bookingservice.model.dto.request.booking.BookingUpdateRequestDTO;
import site.thanhtungle.bookingservice.model.entity.Booking;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;

import java.util.List;

@Transactional
public interface BookingService {

    Booking createBooking(BookingRequestDTO bookingRequestDTO);

    Booking updateBooking(Long bookingId, BookingUpdateRequestDTO bookingUpdateRequestDTO);

    @Transactional(readOnly = true)
    Booking getBooking(Long bookingId);

    @Transactional(readOnly = true)
    PagingApiResponse<List<Booking>> getAllBookings(BaseCriteria bookingCriteria);

    void deleteBooking(Long bookingId);

}
