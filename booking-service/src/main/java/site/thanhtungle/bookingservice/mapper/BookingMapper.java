package site.thanhtungle.bookingservice.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import site.thanhtungle.bookingservice.model.dto.request.booking.BookingRequestDTO;
import site.thanhtungle.bookingservice.model.dto.request.booking.BookingUpdateRequestDTO;
import site.thanhtungle.bookingservice.model.entity.Booking;
import site.thanhtungle.bookingservice.model.entity.BookingItem;
import site.thanhtungle.bookingservice.model.entity.Customer;
import site.thanhtungle.bookingservice.repository.BookingItemRepository;
import site.thanhtungle.bookingservice.repository.CustomerRepository;
import site.thanhtungle.commons.exception.CustomNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.FIELD
)
public abstract class BookingMapper {

    @Autowired
    private BookingItemRepository bookingItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Mapping(target = "bookingItems", source = "bookingItemIds", qualifiedByName = "toEntityBookingItemFromId")
    @Mapping(target = "customers", source = "customerIds", qualifiedByName = "toEntityCustomerFromId")
    public abstract Booking toEntityBooking(BookingRequestDTO bookingRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookingItems", source = "bookingItemIds", qualifiedByName = "toEntityBookingItemFromId")
    @Mapping(target = "customers", source = "customerIds", qualifiedByName = "toEntityCustomerFromId")
    public abstract void updateBooking(BookingUpdateRequestDTO bookingUpdateRequestDTO, @MappingTarget Booking booking);

    @Named("toEntityBookingItemFromId")
    protected Set<BookingItem> toEntityBookingItemFromId(Set<Long> bookingItemIds) {
        if (bookingItemIds == null) return null;
        return bookingItemIds.stream()
                .map(id -> bookingItemRepository
                        .findById(id)
                        .orElseThrow(() -> new CustomNotFoundException("No booking item found with id: " + id))
                )
                .collect(Collectors.toSet());
    }

    @Named("toEntityCustomerFromId")
    protected Set<Customer> toEntityCustomerFromId(Set<Long> customerIds) {
        if (customerIds == null) return null;
        return customerIds.stream()
                .map(id -> customerRepository
                        .findById(id)
                        .orElseThrow(() -> new CustomNotFoundException("No customer found with id: " + id))
                )
                .collect(Collectors.toSet());
    }
}
