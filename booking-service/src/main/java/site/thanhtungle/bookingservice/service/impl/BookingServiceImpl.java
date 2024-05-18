package site.thanhtungle.bookingservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.BookingRequestDTO;
import site.thanhtungle.bookingservice.model.entity.Booking;
import site.thanhtungle.bookingservice.service.BookingService;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    @Override
    public Booking createBooking(BookingRequestDTO bookingRequestDTO) {
        return null;
    }

    @Override
    public Booking updateBooking(Long bookingId) {
        return null;
    }

    @Override
    public Booking getBooking(Long bookingId) {
        return null;
    }

    @Override
    public PagingApiResponse<List<Booking>> getAllBookings(BaseCriteria bookingCriteria) {
        return null;
    }

    @Override
    public void deleteBooking(Long bookingId) {

    }
}
