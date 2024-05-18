package site.thanhtungle.bookingservice.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import site.thanhtungle.bookingservice.model.dto.request.BookingItemRequestDTO;
import site.thanhtungle.bookingservice.model.entity.BookingItem;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.FIELD
)
public interface BookingItemMapper {

    BookingItem toBookingItem(BookingItemRequestDTO bookingItemRequestDTO);
}
