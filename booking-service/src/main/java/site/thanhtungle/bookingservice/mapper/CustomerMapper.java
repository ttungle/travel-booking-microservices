package site.thanhtungle.bookingservice.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import site.thanhtungle.bookingservice.model.dto.request.CustomerRequestDTO;
import site.thanhtungle.bookingservice.model.entity.Booking;
import site.thanhtungle.bookingservice.model.entity.Customer;
import site.thanhtungle.bookingservice.repository.BookingRepository;
import site.thanhtungle.commons.exception.CustomNotFoundException;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.FIELD
)
public abstract class CustomerMapper {

    @Autowired
    private BookingRepository bookingRepository;

    @Mapping(target = "booking", source = "bookingId", qualifiedByName = "toEntityBookingFromId")
    public abstract Customer toEntityCustomer(CustomerRequestDTO customerRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "booking", source = "bookingId", qualifiedByName = "toEntityBookingFromId")
    public abstract void updateCustomer(CustomerRequestDTO customerRequestDTO, @MappingTarget Customer customer);

    @Named("toEntityBookingFromId")
    protected Booking toEntityBookingFromId(Long bookingId) {
        if(bookingId == null) return null;
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CustomNotFoundException("No booking found with id: " + bookingId));
    }
}
