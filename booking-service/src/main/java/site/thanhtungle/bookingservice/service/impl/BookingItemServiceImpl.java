package site.thanhtungle.bookingservice.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import site.thanhtungle.bookingservice.model.dto.response.TourResponseDTO;
import site.thanhtungle.bookingservice.service.rest.TourApiClient;
import site.thanhtungle.commons.constant.enums.EBookingItemStatus;
import site.thanhtungle.bookingservice.mapper.BookingItemMapper;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemRequestDTO;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemStatusRequestDTO;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemUpdateRequestDTO;
import site.thanhtungle.bookingservice.model.dto.request.inventory.BookedQuantityRequestDTO;
import site.thanhtungle.bookingservice.model.entity.BookingItem;
import site.thanhtungle.bookingservice.repository.BookingItemRepository;
import site.thanhtungle.bookingservice.service.BookingItemService;
import site.thanhtungle.bookingservice.service.rest.InventoryApiClient;
import site.thanhtungle.commons.constant.enums.ETourStatus;
import site.thanhtungle.commons.exception.CustomBadRequestException;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.commons.util.CommonPageUtil;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BookingItemServiceImpl implements BookingItemService {

    private final BookingItemMapper bookingItemMapper;
    private final BookingItemRepository bookingItemRepository;
    private final InventoryApiClient inventoryApiClient;
    private final TourApiClient tourApiClient;

    @Override
    public BookingItem createBookingItem(BookingItemRequestDTO bookingItemRequestDTO) {
        Assert.notNull(bookingItemRequestDTO, "The request body should not be empty.");

        ResponseEntity<BaseApiResponse<TourResponseDTO>> response = tourApiClient.getTour(bookingItemRequestDTO.getTourId());
        TourResponseDTO tourResponseDTO = Objects.requireNonNull(response.getBody()).getData();
        if (tourResponseDTO.getStatus() != ETourStatus.ACTIVE) {
            throw new CustomBadRequestException("Cannot create booking because tour is inactive.");
        }

        BookingItem bookingItem = bookingItemMapper.toBookingItem(bookingItemRequestDTO);
        bookingItem.setStatus(EBookingItemStatus.ACTIVE);

        Integer bookedQuantity = getBookedQuantity(bookingItem);
        BookedQuantityRequestDTO requestDTO = new BookedQuantityRequestDTO(bookedQuantity);
        inventoryApiClient.increaseBookedQuantity(bookingItemRequestDTO.getInventoryId(), requestDTO);
        return bookingItemRepository.save(bookingItem);
    }

    @Override
    public BookingItem updateBookingItem(Long bookingItemId, BookingItemUpdateRequestDTO bookingItemRequestDTO) {
        Assert.notNull(bookingItemId, "booking item id cannot be null.");
        
        BookingItem bookingItem = bookingItemRepository.findById(bookingItemId)
                .orElseThrow(() -> new CustomNotFoundException("No booking item found with that id."));
        Integer oldBookingQuantity = getBookedQuantity(bookingItem);
        bookingItemMapper.updateBookingItem(bookingItemRequestDTO, bookingItem);

        ResponseEntity<BaseApiResponse<TourResponseDTO>> response = tourApiClient.getTour(bookingItem.getTourId());
        TourResponseDTO tourResponseDTO = Objects.requireNonNull(response.getBody()).getData();
        if (tourResponseDTO.getStatus() != ETourStatus.ACTIVE) {
            throw new CustomBadRequestException("Cannot update booking for inactive tour.");
        }

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
    public String batchUpdateBookingItemStatus(Long tourId, BookingItemStatusRequestDTO bookingItemStatusRequestDTO) {
        Assert.notNull(tourId, "tourId cannot be null.");
        List<BookingItem> bookingItemList = bookingItemRepository.findByTourId(tourId);

        if (bookingItemList.isEmpty()) throw new CustomNotFoundException("No booking item found with that tourId.");
        bookingItemList.forEach(bookingItem -> {
            if (bookingItem.getStatus() != bookingItemStatusRequestDTO.getStatus()) {
                bookingItem.setStatus(bookingItemStatusRequestDTO.getStatus());
                bookingItemRepository.save(bookingItem);
            }
        });
        return String.format("The status of %s booking items has been successfully updated.", bookingItemList.size());
    }

    @Override
    public BookingItem getBookingItem(Long bookingItemId) {
        Assert.notNull(bookingItemId, "Booking item id cannot be null.");
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
        Assert.notNull(bookingItemId, "Booking item id cannot be null.");

        BookingItem bookingItem = bookingItemRepository.findById(bookingItemId)
                .orElseThrow(() -> new CustomNotFoundException("No booking item found with that id."));

        Integer bookedQuantity = getBookedQuantity(bookingItem);
        BookedQuantityRequestDTO requestDTO = new BookedQuantityRequestDTO(bookedQuantity);
        inventoryApiClient.decreaseBookedQuantity(bookingItem.getInventoryId(), requestDTO);

        bookingItemRepository.deleteById(bookingItem.getId());
    }

    @NotNull
    public static Integer getBookedQuantity(BookingItem bookingItem) {
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
