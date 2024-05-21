package site.thanhtungle.bookingservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import site.thanhtungle.bookingservice.mapper.BookingMapper;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.BookingRequestDTO;
import site.thanhtungle.bookingservice.model.entity.Booking;
import site.thanhtungle.bookingservice.repository.BookingRepository;
import site.thanhtungle.bookingservice.service.BookingService;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.commons.util.CommonPageUtil;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;

    private BookingMapper bookingMapper;

    @Override
    public Booking createBooking(BookingRequestDTO bookingRequestDTO) {
        if (bookingRequestDTO == null) throw new InvalidParameterException("The request body should not be empty.");
        if (bookingRequestDTO.getBookingItemIds().isEmpty())
            throw new InvalidParameterException("Booking should contain at least one booking item.");
        Booking booking = bookingMapper.toEntityBooking(bookingRequestDTO);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingRequestDTO bookingRequestDTO) {
        if (bookingId == null) throw new InvalidParameterException("booking item id cannot be null.");

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CustomNotFoundException("No booking found with that id."));
        bookingMapper.updateBooking(bookingRequestDTO, booking);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBooking(Long bookingId) {
        if (bookingId == null) throw new InvalidParameterException("booking item id cannot be null.");
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CustomNotFoundException("No booking found with that id."));
    }

    @Override
    public PagingApiResponse<List<Booking>> getAllBookings(BaseCriteria bookingCriteria) {
        PageRequest pageRequest = CommonPageUtil.getPageRequest(
                bookingCriteria.getPage(),
                bookingCriteria.getPageSize(),
                bookingCriteria.getSort()
        );

        Page<Booking> bookingListPaging = bookingRepository.findAll(pageRequest);
        List<Booking> bookingList = bookingListPaging.getContent();
        PageInfo pageInfo = new PageInfo(bookingCriteria.getPage(), bookingCriteria.getPageSize(),
                bookingListPaging.getTotalElements(), bookingListPaging.getTotalPages());
        return new PagingApiResponse<>(HttpStatus.OK.value(), bookingList, pageInfo);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        if (bookingId == null) throw new InvalidParameterException("booking item id cannot be null.");
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CustomNotFoundException("No booking found with that id."));
        bookingRepository.deleteById(booking.getId());
    }
}
