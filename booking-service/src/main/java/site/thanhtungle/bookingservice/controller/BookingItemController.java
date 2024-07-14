package site.thanhtungle.bookingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("${api.url.bookingItems}")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class BookingItemController {

    private BookingItemService bookingItemService;

    @Operation(summary = "Create new booking item")
    @PostMapping
    public ResponseEntity<BaseApiResponse<BookingItem>> createBookingItem(Principal principal,
            @Valid @RequestBody BookingItemRequestDTO bookingItemRequestDTO) {
        BookingItem bookingItem = bookingItemService.createBookingItem(principal.getName(), bookingItemRequestDTO);
        BaseApiResponse<BookingItem> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), bookingItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update booking item")
    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<BookingItem>> updateBookingItem(
            @PathVariable("id") Long bookingItemId, @Valid @RequestBody BookingItemUpdateRequestDTO bookingItemRequestDTO) {
        BookingItem bookingItem = bookingItemService.updateBookingItem(bookingItemId, bookingItemRequestDTO);
        BaseApiResponse<BookingItem> response = new BaseApiResponse<>(HttpStatus.OK.value(), bookingItem);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Batch update booking item status", description = "Update all booking item statuses related to the tour.")
    @PutMapping("/tours/{id}")
    public ResponseEntity<BaseApiResponse<List<BookingItem>>> batchUpdateBookingItemStatus(
            @PathVariable("id") Long tourId, @Valid @RequestBody BookingItemStatusRequestDTO bookingItemStatusRequestDTO) {
        List<BookingItem> bookingItemList = bookingItemService.batchUpdateBookingItemStatus(tourId, bookingItemStatusRequestDTO);
        BaseApiResponse<List<BookingItem>> response = new BaseApiResponse<>(HttpStatus.OK.value(), bookingItemList);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get booking item by id")
    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<BookingItem>> getBookingItem(@PathVariable("id") Long bookingItemId) {
        BookingItem bookingItem = bookingItemService.getBookingItem(bookingItemId);
        BaseApiResponse<BookingItem> response = new BaseApiResponse<>(HttpStatus.OK.value(), bookingItem);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get all booking items", description = "Get all booking items with pagination and sort.")
    @GetMapping
    public ResponseEntity<PagingApiResponse<List<BookingItem>>> getAllBookingItems(@Valid BaseCriteria bookingItemCriteria) {
        PagingApiResponse<List<BookingItem>> response = bookingItemService.getAllBookingItems(bookingItemCriteria);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Check booking item exist by tourId", description = "Check if any booking items related to the tour exist.")
    @GetMapping("/tours/{id}")
    public boolean checkBookingItemExistByTourId(@PathVariable("id") Long tourId) {
        return bookingItemService.checkBookingItemExistByTourId(tourId);
    }

    @Operation(summary = "Delete booking item by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookingItem(@PathVariable("id") Long bookingItemId) {
        bookingItemService.deleteBookingItem(bookingItemId);
    }
}
