package site.thanhtungle.paymentservice.service.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.paymentservice.model.dto.Booking;
import site.thanhtungle.paymentservice.model.dto.BookingUpdateRequestDTO;

@FeignClient(name = "BOOKING-SERVICE", path = "/api/v1/bookings")
public interface BookingApiClient {

    @GetMapping("/{id}")
    ResponseEntity<BaseApiResponse<Booking>> getBooking(@PathVariable("id") Long bookingId);

    @PutMapping("/{id}")
    ResponseEntity<BaseApiResponse<Booking>> updateBooking(@PathVariable("id") Long bookingId,
                                                                  @RequestBody BookingUpdateRequestDTO bookingRequestDTO);
}
