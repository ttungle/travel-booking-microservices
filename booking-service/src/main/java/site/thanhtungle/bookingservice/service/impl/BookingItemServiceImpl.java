package site.thanhtungle.bookingservice.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import site.thanhtungle.bookingservice.mapper.BookingItemMapper;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemUpdateRequestDTO;
import site.thanhtungle.bookingservice.model.dto.request.inventory.BookedQuantityRequestDTO;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemRequestDTO;
import site.thanhtungle.bookingservice.model.entity.BookingItem;
import site.thanhtungle.bookingservice.repository.BookingItemRepository;
import site.thanhtungle.bookingservice.service.BookingItemService;
import site.thanhtungle.bookingservice.service.rest.InventoryApiClient;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.commons.util.CommonPageUtil;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingItemServiceImpl implements BookingItemService {

    private BookingItemMapper bookingItemMapper;
    private BookingItemRepository bookingItemRepository;
    private InventoryApiClient inventoryApiClient;

    @Override
    public BookingItem createBookingItem(BookingItemRequestDTO bookingItemRequestDTO) {
        if (bookingItemRequestDTO == null) throw new IllegalArgumentException("The request body should not be empty.");
        BookingItem bookingItem = bookingItemMapper.toBookingItem(bookingItemRequestDTO);

        Integer bookedQuantity = getBookedQuantity(bookingItem);
        BookedQuantityRequestDTO requestDTO = new BookedQuantityRequestDTO(bookedQuantity);
        inventoryApiClient.increaseBookedQuantity(bookingItemRequestDTO.getInventoryId(), requestDTO);

        return bookingItemRepository.save(bookingItem);
    }

    @Override
    public BookingItem updateBookingItem(Long bookingItemId, BookingItemUpdateRequestDTO bookingItemRequestDTO) {
        if (bookingItemId == null) throw new IllegalArgumentException("booking item id cannot be null.");
        
        BookingItem bookingItem = bookingItemRepository.findById(bookingItemId)
                .orElseThrow(() -> new CustomNotFoundException("No booking item found with that id."));
        Integer oldBookingQuantity = getBookedQuantity(bookingItem);
        bookingItemMapper.updateBookingItem(bookingItemRequestDTO, bookingItem);
        Integer newBookingQuantity = getBookedQuantity(bookingItem);

        if (newBookingQuantity > oldBookingQuantity) {
            BookedQuantityRequestDTO requestDTO = new BookedQuantityRequestDTO(newBookingQuantity - oldBookingQuantity);
            inventoryApiClient.increaseBookedQuantity(bookingItem.getInventoryId(), requestDTO);
        }

        if (newBookingQuantity < oldBookingQuantity) {
            BookedQuantityRequestDTO requestDTO = new BookedQuantityRequestDTO(oldBookingQuantity - newBookingQuantity);
            inventoryApiClient.decreaseBookedQuantity(bookingItem.getInventoryId(), requestDTO);
        }

        return bookingItemRepository.save(bookingItem);
    }

    @Override
    public BookingItem getBookingItem(Long bookingItemId) {
        if (bookingItemId == null) throw new IllegalArgumentException("Booking item id cannot be null.");
        return bookingItemRepository.findById(bookingItemId)
                .orElseThrow(() -> new CustomNotFoundException("No booking item found with that id."));
    }

    @Override
    public PagingApiResponse<List<BookingItem>> getAllBookingItems(BaseCriteria bookingItemCriteria) {
        PageRequest pageRequest = CommonPageUtil.getPageRequest(
                bookingItemCriteria.getPage(),
                bookingItemCriteria.getPageSize(),
                bookingItemCriteria.getSort()
        );

        Page<BookingItem> bookingItemListPaging = bookingItemRepository.findAll(pageRequest);
        List<BookingItem> bookingItemList = bookingItemListPaging.getContent();
        PageInfo pageInfo = new PageInfo(bookingItemCriteria.getPage(), bookingItemCriteria.getPageSize(),
                bookingItemListPaging.getTotalElements(), bookingItemListPaging.getTotalPages());
        return new PagingApiResponse<>(HttpStatus.OK.value(), bookingItemList, pageInfo);
    }

    @Override
    public void deleteBookingItem(Long bookingItemId) {
        if (bookingItemId == null) throw new IllegalArgumentException("Booking item id cannot be null.");

        BookingItem bookingItem = bookingItemRepository.findById(bookingItemId)
                .orElseThrow(() -> new CustomNotFoundException("No booking item found with that id."));

        Integer bookedQuantity = getBookedQuantity(bookingItem);
        BookedQuantityRequestDTO requestDTO = new BookedQuantityRequestDTO(bookedQuantity);
        inventoryApiClient.decreaseBookedQuantity(bookingItem.getInventoryId(), requestDTO);

        bookingItemRepository.deleteById(bookingItem.getId());
    }

    @NotNull
    private static Integer getBookedQuantity(BookingItem bookingItem) {
        if (bookingItem.getAdultQuantity() != null && bookingItem.getChildQuantity() != null) {
            return bookingItem.getAdultQuantity() + bookingItem.getChildQuantity();
        }

        if (bookingItem.getAdultQuantity() != null) {
            return bookingItem.getAdultQuantity();
        }

        if (bookingItem.getChildQuantity() != null) {
            return bookingItem.getChildQuantity();
        }
        return 0;
    }
}
