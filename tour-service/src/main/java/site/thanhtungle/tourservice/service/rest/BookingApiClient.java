package site.thanhtungle.tourservice.service.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.tourservice.model.dto.request.booking.BookingItemStatusRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.booking.BookingItem;

import java.util.List;

@FeignClient(name = "BOOKING-SERVICE", path = "/api/v1/bookings/items")
public interface BookingApiClient {

    @PutMapping("/tours/{id}")
    ResponseEntity<BaseApiResponse<List<BookingItem>>> batchUpdateBookingItemStatus(
            @PathVariable("id") Long tourId, @RequestBody BookingItemStatusRequestDTO bookingItemStatusRequestDTO);

    @GetMapping("/tours/{id}")
    boolean checkBookingItemExistByTourId(@PathVariable("id") Long tourId);
}
