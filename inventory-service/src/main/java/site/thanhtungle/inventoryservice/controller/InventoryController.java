package site.thanhtungle.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.inventoryservice.model.criteria.InventoryCriteria;
import site.thanhtungle.inventoryservice.model.dto.BookedQuantityRequestDTO;
import site.thanhtungle.inventoryservice.model.dto.InventoryRequestDTO;
import site.thanhtungle.inventoryservice.model.dto.InventoryUpdateRequestDTO;
import site.thanhtungle.inventoryservice.model.entity.Inventory;
import site.thanhtungle.inventoryservice.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("${api.url.inventory}")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class InventoryController {

    private InventoryService inventoryService;

    @Operation(summary = "Create new inventory")
    @PostMapping
    public ResponseEntity<BaseApiResponse<Inventory>> createInventory(
            @Valid @RequestBody InventoryRequestDTO inventoryRequestDTO
    ) {
        Inventory inventory = inventoryService.createInventory(inventoryRequestDTO);
        BaseApiResponse<Inventory> response = new BaseApiResponse<>(HttpStatus.OK.value(), inventory);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update inventory")
    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Inventory>> updateInventory(
            @PathVariable("id") Long inventoryId,
            @RequestBody InventoryUpdateRequestDTO inventoryUpdateRequestDTO
    ) {
        Inventory inventory = inventoryService.updateInventory(inventoryId, inventoryUpdateRequestDTO);
        BaseApiResponse<Inventory> response = new BaseApiResponse<>(HttpStatus.OK.value(), inventory);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Increase booked quantity", description = "Increase booked quantity of a tour, the availableQuantity " +
        "and availableQuantity will be calculated automatically."
    )
    @PutMapping("/{id}/bookedQuantity/increase")
    public ResponseEntity<BaseApiResponse<Inventory>> increaseBookedQuantity(
            @PathVariable("id") Long inventoryId, @Valid @RequestBody BookedQuantityRequestDTO requestDTO) {
        Inventory inventory = inventoryService.increaseBookedQuantity(inventoryId, requestDTO);
        BaseApiResponse<Inventory> response = new BaseApiResponse<>(HttpStatus.OK.value(), inventory);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Decrease booked quantity", description = "Decrease booked quantity of a tour, the availableQuantity " +
            "and availableQuantity will be calculated automatically."
    )
    @PutMapping("/{id}/bookedQuantity/decrease")
    public ResponseEntity<BaseApiResponse<Inventory>> decreaseBookedQuantity(
            @PathVariable("id") Long inventoryId, @Valid @RequestBody BookedQuantityRequestDTO requestDTO) {
        Inventory inventory = inventoryService.decreaseBookedQuantity(inventoryId, requestDTO);
        BaseApiResponse<Inventory> response = new BaseApiResponse<>(HttpStatus.OK.value(), inventory);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get inventory by id")
    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Inventory>> getInventory(@PathVariable("id") Long inventoryId) {
        Inventory inventory = inventoryService.getInventory(inventoryId);
        BaseApiResponse<Inventory> response = new BaseApiResponse<>(HttpStatus.OK.value(), inventory);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get all inventories", description = "Get all inventories with pagination, sort and filters" +
        "Support filter fields: startDate, bookedQuantity, availableQuantity, totalQuantity, tourId."
    )
    @GetMapping()
    public ResponseEntity<PagingApiResponse<List<Inventory>>> getAllInventory(@Valid InventoryCriteria inventoryCriteria) {
        PagingApiResponse<List<Inventory>> listPagingApiResponse = inventoryService.getAllInventory(inventoryCriteria);
        return ResponseEntity.ok().body(listPagingApiResponse);
    }

    @Operation(summary = "Delete inventory by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable("id") Long inventoryId) {
        inventoryService.deleteInventory(inventoryId);
    }
}
