package site.thanhtungle.bookingservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import site.thanhtungle.commons.constant.enums.EBookingStatus;
import site.thanhtungle.bookingservice.mapper.BookingMapper;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.booking.BookingRequestDTO;
import site.thanhtungle.bookingservice.model.dto.request.booking.BookingUpdateRequestDTO;
import site.thanhtungle.bookingservice.model.dto.request.inventory.BookedQuantityRequestDTO;
import site.thanhtungle.bookingservice.model.entity.Booking;
import site.thanhtungle.bookingservice.repository.BookingRepository;
import site.thanhtungle.bookingservice.service.BookingService;
import site.thanhtungle.bookingservice.service.rest.InventoryApiClient;
import site.thanhtungle.commons.exception.CustomBadRequestException;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.commons.util.CommonPageUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static site.thanhtungle.bookingservice.service.impl.BookingItemServiceImpl.getBookedQuantity;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final InventoryApiClient inventoryApiClient;

    @Override
    public Booking createBooking(String userId, BookingRequestDTO bookingRequestDTO) {
        Assert.notNull(bookingRequestDTO, "The request body cannot be empty.");

        Booking booking = bookingMapper.toEntityBooking(bookingRequestDTO, userId);
        // new booking will have pending status by default
        booking.setStatus(EBookingStatus.PENDING);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingUpdateRequestDTO bookingUpdateRequestDTO) {
        Assert.notNull(bookingId, "bookingId cannot be null.");

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CustomNotFoundException("No booking found with that id."));
        boolean isStatusUpdate = checkStatusUpdate(bookingUpdateRequestDTO, booking);
        // Only pending status able to add new booking items
        if (
                booking.getStatus() != EBookingStatus.PENDING &&
                Objects.nonNull(bookingUpdateRequestDTO.getBookingItemIds()) &&
                !bookingUpdateRequestDTO.getBookingItemIds().isEmpty()
        ) {
            throw new CustomBadRequestException("bookingItems can only be added to booking that are in PENDING status.");
        }

        bookingMapper.updateBooking(bookingUpdateRequestDTO, booking);
        updateInventory(booking, isStatusUpdate);

        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBooking(Long bookingId) {
        Assert.notNull(bookingId, "bookingId cannot be null.");
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
        Assert.notNull(bookingId, "bookingId cannot be null.");
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CustomNotFoundException("No booking found with that id."));
        bookingRepository.deleteById(booking.getId());
    }

    private void updateInventory(Booking booking, boolean isStatusUpdate) {
        // if status in void, cancel -> release inventory
        EBookingStatus bookingStatus = booking.getStatus();
        List<EBookingStatus> unblockedStatus = Arrays.asList(EBookingStatus.VOID, EBookingStatus.CANCELLED);
        List<EBookingStatus> blockedStatus = Arrays.asList(EBookingStatus.PENDING,EBookingStatus.PRE_BOOKING, EBookingStatus.PAID);
        if (isStatusUpdate && unblockedStatus.contains(bookingStatus)) {
            booking.getBookingItems().forEach(bookingItem -> {
                if (bookingItem.getInventoryId() != null) {
                    Integer bookedQuantity = getBookedQuantity(bookingItem);
                    BookedQuantityRequestDTO requestDTO = new BookedQuantityRequestDTO(bookedQuantity);
                    inventoryApiClient.decreaseBookedQuantity(bookingItem.getInventoryId(), requestDTO);
                }
            });
        }
        // if status in remaining status -> add inventory for each booking items
        if (isStatusUpdate && blockedStatus.contains(bookingStatus)) {
            booking.getBookingItems().forEach(bookingItem -> {
                if (bookingItem.getInventoryId() != null) {
                    Integer bookedQuantity = getBookedQuantity(bookingItem);
                    BookedQuantityRequestDTO requestDTO = new BookedQuantityRequestDTO(bookedQuantity);
                    inventoryApiClient.increaseBookedQuantity(bookingItem.getInventoryId(), requestDTO);
                }
            });
        }
    }

    private boolean checkStatusUpdate(BookingUpdateRequestDTO bookingUpdateRequestDTO, Booking booking) {
        if (Objects.isNull(bookingUpdateRequestDTO) || Objects.isNull(booking)) return false;
        if (Objects.isNull(bookingUpdateRequestDTO.getStatus())) return false;
        if (bookingUpdateRequestDTO.getStatus() == booking.getStatus()) return false;

        List<EBookingStatus> unblockedStatus = Arrays.asList(EBookingStatus.VOID, EBookingStatus.CANCELLED);
        List<EBookingStatus> blockedStatus = Arrays.asList(EBookingStatus.PENDING,EBookingStatus.PRE_BOOKING, EBookingStatus.PAID);
        if (unblockedStatus.contains(bookingUpdateRequestDTO.getStatus()) && unblockedStatus.contains(booking.getStatus())) {
            return false;
        }

        if (blockedStatus.contains(bookingUpdateRequestDTO.getStatus()) && blockedStatus.contains(booking.getStatus())) {
            return false;
        }

        return true;
    }
}
