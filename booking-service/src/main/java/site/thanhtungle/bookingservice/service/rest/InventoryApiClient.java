package site.thanhtungle.bookingservice.service.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import site.thanhtungle.bookingservice.model.dto.request.inventory.BookedQuantityRequestDTO;
import site.thanhtungle.bookingservice.model.dto.response.InventoryDTO;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;

@FeignClient(name = "INVENTORY-SERVICE", path = "/api/v1/inventory")
public interface InventoryApiClient {

    @PutMapping("/{id}/bookedQuantity/increase")
    ResponseEntity<BaseApiResponse<InventoryDTO>> increaseBookedQuantity(
            @PathVariable("id") Long inventoryId, @RequestBody BookedQuantityRequestDTO requestDTO);

    @PutMapping("/{id}/bookedQuantity/decrease")
    ResponseEntity<BaseApiResponse<InventoryDTO>> decreaseBookedQuantity(
            @PathVariable("id") Long inventoryId, @RequestBody BookedQuantityRequestDTO requestDTO);
}
