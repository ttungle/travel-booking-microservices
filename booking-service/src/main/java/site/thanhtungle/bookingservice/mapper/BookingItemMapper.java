package site.thanhtungle.bookingservice.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemRequestDTO;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemUpdateRequestDTO;
import site.thanhtungle.bookingservice.model.entity.Booking;
import site.thanhtungle.bookingservice.model.entity.BookingItem;
import site.thanhtungle.bookingservice.repository.BookingRepository;
import site.thanhtungle.commons.exception.CustomNotFoundException;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.FIELD
)
public abstract class BookingItemMapper {

    @Autowired
    private BookingRepository bookingRepository;

    @Mapping(target = "booking", source = "bookingItemRequestDTO.bookingId", qualifiedByName = "toEntityBookingFromId")
    @Mapping(target = "userId", source = "userId")
    public abstract BookingItem toBookingItem(BookingItemRequestDTO bookingItemRequestDTO, String userId);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "booking", source = "bookingId", qualifiedByName = "toEntityBookingFromId")
    public abstract void updateBookingItem(BookingItemUpdateRequestDTO bookingItemRequestDTO,
                                           @MappingTarget BookingItem bookingItem);

    @Named("toEntityBookingFromId")
    protected Booking toEntityBookingFromId(Long bookingId) {
        if (bookingId == null ) return null;
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CustomNotFoundException("No booking found with id: " + bookingId));
    }
}
