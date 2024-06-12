package site.thanhtungle.bookingservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemRequestDTO;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemStatusRequestDTO;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemUpdateRequestDTO;
import site.thanhtungle.bookingservice.model.entity.BookingItem;
import site.thanhtungle.bookingservice.service.BookingItemService;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;

import java.util.List;

@RestController
@RequestMapping("${api.url.bookingItems}")
@AllArgsConstructor
public class BookingItemController {

    private BookingItemService bookingItemService;

    @PostMapping
    public ResponseEntity<BaseApiResponse<BookingItem>> createBookingItem(
            @Valid @RequestBody BookingItemRequestDTO bookingItemRequestDTO) {
        BookingItem bookingItem = bookingItemService.createBookingItem(bookingItemRequestDTO);
        BaseApiResponse<BookingItem> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), bookingItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<BookingItem>> updateBookingItem(
            @PathVariable("id") Long bookingItemId, @Valid @RequestBody BookingItemUpdateRequestDTO bookingItemRequestDTO) {
        BookingItem bookingItem = bookingItemService.updateBookingItem(bookingItemId, bookingItemRequestDTO);
        BaseApiResponse<BookingItem> response = new BaseApiResponse<>(HttpStatus.OK.value(), bookingItem);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/tours/{id}")
    public ResponseEntity<BaseApiResponse<String>> batchUpdateBookingItemStatus(
            @PathVariable("id") Long tourId, @Valid @RequestBody BookingItemStatusRequestDTO bookingItemStatusRequestDTO) {
        String message = bookingItemService.batchUpdateBookingItemStatus(tourId, bookingItemStatusRequestDTO);
        BaseApiResponse<String> response = new BaseApiResponse<>(HttpStatus.OK.value(), message);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<BookingItem>> getBookingItem(@PathVariable("id") Long bookingItemId) {
        BookingItem bookingItem = bookingItemService.getBookingItem(bookingItemId);
        BaseApiResponse<BookingItem> response = new BaseApiResponse<>(HttpStatus.OK.value(), bookingItem);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<PagingApiResponse<List<BookingItem>>> getAllBookingItems(@Valid BaseCriteria bookingItemCriteria) {
        PagingApiResponse<List<BookingItem>> response = bookingItemService.getAllBookingItems(bookingItemCriteria);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/tours/{id}")
    public boolean checkBookingItemExistByTourId(@PathVariable("id") Long tourId) {
        return bookingItemService.checkBookingItemExistByTourId(tourId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookingItem(@PathVariable("id") Long bookingItemId) {
        bookingItemService.deleteBookingItem(bookingItemId);
    }
}
