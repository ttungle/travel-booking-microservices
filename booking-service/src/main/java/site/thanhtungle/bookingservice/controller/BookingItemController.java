package site.thanhtungle.bookingservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemRequestDTO;
import site.thanhtungle.bookingservice.model.entity.BookingItem;
import site.thanhtungle.bookingservice.service.BookingItemService;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;

@RestController
@RequestMapping("/api/v1/bookingItems")
@AllArgsConstructor
public class BookingItemController {

    private BookingItemService bookingItemService;

    @PostMapping
    public ResponseEntity<BaseApiResponse<BookingItem>> createBookingItem(
            @RequestBody BookingItemRequestDTO bookingItemRequestDTO) {
        return null;
    }

    @PutMapping("/{id}")
    public BaseApiResponse<ResponseEntity<BookingItem>> updateBookingItem(@PathVariable("id") Long bookingItemId) {
        return null;
    }

    @GetMapping("/{id}")
    public BaseApiResponse<ResponseEntity<BookingItem>> getBookingItem(@PathVariable("id") Long bookingItemId) {
        return null;
    }

    @GetMapping
    public BaseApiResponse<ResponseEntity<BookingItem>> getAllBookingItems(@Valid BaseCriteria bookingItemCriteria) {
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookingItem(@PathVariable("id") Long bookingItemId) {
    }
}
