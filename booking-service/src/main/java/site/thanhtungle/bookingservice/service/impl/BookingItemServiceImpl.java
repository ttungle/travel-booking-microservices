package site.thanhtungle.bookingservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import site.thanhtungle.bookingservice.mapper.BookingItemMapper;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemRequestDTO;
import site.thanhtungle.bookingservice.model.entity.BookingItem;
import site.thanhtungle.bookingservice.repository.BookingItemRepository;
import site.thanhtungle.bookingservice.service.BookingItemService;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.commons.util.CommonPageUtil;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingItemServiceImpl implements BookingItemService {

    private BookingItemMapper bookingItemMapper;
    private BookingItemRepository bookingItemRepository;

    @Override
    public BookingItem createBookingItem(BookingItemRequestDTO bookingItemRequestDTO) {
        if (bookingItemRequestDTO == null) throw new InvalidParameterException("The request body should not be empty.");
        BookingItem bookingItem = bookingItemMapper.toBookingItem(bookingItemRequestDTO);
        return bookingItemRepository.save(bookingItem);
    }

    @Override
    public BookingItem updateBookingItem(Long bookingItemId, BookingItemRequestDTO bookingItemRequestDTO) {
        if (bookingItemId == null) throw new InvalidParameterException("booking item id cannot be null.");

        BookingItem bookingItem = bookingItemRepository.findById(bookingItemId)
                .orElseThrow(() -> new CustomNotFoundException("No booking item found with that id."));
        bookingItemMapper.updateBookingItem(bookingItemRequestDTO, bookingItem);
        return bookingItemRepository.save(bookingItem);
    }

    @Override
    public BookingItem getBookingItem(Long bookingItemId) {
        if (bookingItemId == null) throw new InvalidParameterException("Booking item id cannot be null.");
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
        if (bookingItemId == null) throw new InvalidParameterException("Booking item id cannot be null.");
        BookingItem bookingItem = bookingItemRepository.findById(bookingItemId)
                .orElseThrow(() -> new CustomNotFoundException("No booking item found with that id."));
        bookingItemRepository.deleteById(bookingItem.getId());
    }
}
