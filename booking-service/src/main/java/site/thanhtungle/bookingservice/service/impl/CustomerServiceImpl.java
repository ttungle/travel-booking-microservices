package site.thanhtungle.bookingservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import site.thanhtungle.bookingservice.mapper.CustomerMapper;
import site.thanhtungle.bookingservice.model.criteria.BaseCriteria;
import site.thanhtungle.bookingservice.model.dto.request.CustomerRequestDTO;
import site.thanhtungle.bookingservice.model.entity.Customer;
import site.thanhtungle.bookingservice.repository.CustomerRepository;
import site.thanhtungle.bookingservice.service.CustomerService;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.commons.util.CommonPageUtil;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Customer createCustomer(CustomerRequestDTO customerRequestDTO) {
        if (customerRequestDTO == null) throw new IllegalArgumentException("Request body cannot be null.");
        Customer customer = customerMapper.toEntityCustomer(customerRequestDTO);
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long customerId, CustomerRequestDTO customerRequestDTO) {
        if (customerId == null) throw new IllegalArgumentException("Customer id cannot be null.");
        if (customerRequestDTO == null) throw new IllegalArgumentException("Request body cannot be null.");

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomNotFoundException("No customer found with that id."));
        customerMapper.updateCustomer(customerRequestDTO, customer);
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long customerId) {
        if (customerId == null) throw new IllegalArgumentException("Customer id cannot be null.");
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomNotFoundException("No customer found with that id."));
    }

    @Override
    public PagingApiResponse<List<Customer>> getAllCustomersByBookingId(Long bookingId, BaseCriteria baseCriteria) {
        PageRequest pageRequest = CommonPageUtil.getPageRequest(
                baseCriteria.getPage(),
                baseCriteria.getPageSize(),
                baseCriteria.getSort()
        );

        Page<Customer> customerListPaging = customerRepository.findByBookingId(bookingId, pageRequest);
        List<Customer> customerList = customerListPaging.getContent();
        PageInfo pageInfo = new PageInfo(baseCriteria.getPage(), baseCriteria.getPageSize(),
                customerListPaging.getTotalElements(), customerListPaging.getTotalPages());
        return new PagingApiResponse<>(HttpStatus.OK.value(), customerList, pageInfo);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        if (customerId == null) throw new IllegalArgumentException("Customer id cannot be null.");
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomNotFoundException("No customer found with that id."));
        customerRepository.deleteById(customer.getId());
    }
}
