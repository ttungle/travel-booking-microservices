package site.thanhtungle.bookingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.bookingservice.model.dto.request.CustomerRequestDTO;
import site.thanhtungle.bookingservice.model.entity.Customer;
import site.thanhtungle.bookingservice.service.CustomerService;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;

@RestController
@RequestMapping("${api.url.bookingCustomers}")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class CustomerController {

    private CustomerService customerService;

    @Operation(summary = "Create new customer information")
    @PostMapping
    public ResponseEntity<BaseApiResponse<Customer>> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerService.createCustomer(customerRequestDTO);
        BaseApiResponse<Customer> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), customer);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update customer information")
    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Customer>> updateCustomer(@PathVariable("id") Long customerId,
                                                                    @RequestBody CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerService.updateCustomer(customerId, customerRequestDTO);
        BaseApiResponse<Customer> response = new BaseApiResponse<>(HttpStatus.OK.value(), customer);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get customer information by id")
    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Customer>> getCustomer(@PathVariable("id") Long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        BaseApiResponse<Customer> response = new BaseApiResponse<>(HttpStatus.OK.value(), customer);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete customer information by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") Long customerId) {
        customerService.deleteCustomer(customerId);
    }
}
