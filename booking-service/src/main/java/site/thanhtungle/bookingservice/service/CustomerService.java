package site.thanhtungle.bookingservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.CustomerRequestDTO;
import site.thanhtungle.bookingservice.model.entity.Customer;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;

import java.util.List;

@Transactional
public interface CustomerService {

    Customer createCustomer(CustomerRequestDTO customerRequestDTO);

    Customer updateCustomer(Long customerId, CustomerRequestDTO customerRequestDTO);

    @Transactional(readOnly = true)
    Customer getCustomer(Long customerId);

    @Transactional(readOnly = true)
    PagingApiResponse<List<Customer>> getAllCustomersByBookingId(Long bookingId, BaseCriteria baseCriteria);

    void deleteCustomer(Long customerId);
}
