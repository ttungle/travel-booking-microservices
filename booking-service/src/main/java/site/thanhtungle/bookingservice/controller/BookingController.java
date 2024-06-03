package site.thanhtungle.bookingservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.BookingRequestDTO;
import site.thanhtungle.bookingservice.model.entity.Booking;
import site.thanhtungle.bookingservice.model.entity.Customer;
import site.thanhtungle.bookingservice.service.BookingService;
import site.thanhtungle.bookingservice.service.CustomerService;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;

import java.util.List;

@RestController
@RequestMapping("${api.url.booking}")
@AllArgsConstructor
public class BookingController {

    private BookingService bookingService;

    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<BaseApiResponse<Booking>> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
        Booking booking = bookingService.createBooking(bookingRequestDTO);
        BaseApiResponse<Booking> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Booking>> updateBooking(@PathVariable("id") Long bookingId,
                                                                  @RequestBody BookingRequestDTO bookingRequestDTO) {
        Booking booking = bookingService.updateBooking(bookingId, bookingRequestDTO);
        BaseApiResponse<Booking> response = new BaseApiResponse<>(HttpStatus.OK.value(), booking);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Booking>> getBooking(@PathVariable("id") Long bookingId) {
        Booking booking = bookingService.getBooking(bookingId);
        BaseApiResponse<Booking> response = new BaseApiResponse<>(HttpStatus.OK.value(), booking);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<PagingApiResponse<List<Booking>>> getAllBooking(@Valid BaseCriteria bookingCriteria) {
        PagingApiResponse<List<Booking>> response = bookingService.getAllBookings(bookingCriteria);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable("id") Long bookingId) {
        bookingService.deleteBooking(bookingId);
    }

    @GetMapping("/{id}/customers")
    public ResponseEntity<PagingApiResponse<List<Customer>>> getAllCustomersByBookingId(
            @PathVariable("id") Long customerId, @Valid BaseCriteria baseCriteria) {
        PagingApiResponse<List<Customer>> response = customerService.getAllCustomersByBookingId(customerId, baseCriteria);
        return ResponseEntity.ok().body(response);
    }
}
