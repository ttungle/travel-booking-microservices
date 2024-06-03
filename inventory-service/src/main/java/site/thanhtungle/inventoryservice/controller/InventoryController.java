package site.thanhtungle.inventoryservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.inventoryservice.model.criteria.InventoryCriteria;
import site.thanhtungle.inventoryservice.model.dto.InventoryRequestDTO;
import site.thanhtungle.inventoryservice.model.dto.InventoryUpdateRequestDTO;
import site.thanhtungle.inventoryservice.model.entity.Inventory;
import site.thanhtungle.inventoryservice.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("${api.url.inventory}")
@AllArgsConstructor
public class InventoryController {

    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<BaseApiResponse<Inventory>> createInventory(
            @Valid @RequestBody InventoryRequestDTO inventoryRequestDTO
    ) {
        Inventory inventory = inventoryService.createInventory(inventoryRequestDTO);
        BaseApiResponse<Inventory> response = new BaseApiResponse<>(HttpStatus.OK.value(), inventory);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Inventory>> updateInventory(
            @PathVariable("id") Long inventoryId,
            @RequestBody InventoryUpdateRequestDTO inventoryUpdateRequestDTO
    ) {
        Inventory inventory = inventoryService.updateInventory(inventoryId, inventoryUpdateRequestDTO);
        BaseApiResponse<Inventory> response = new BaseApiResponse<>(HttpStatus.OK.value(), inventory);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Inventory>> getInventory(@PathVariable("id") Long inventoryId) {
        Inventory inventory = inventoryService.getInventory(inventoryId);
        BaseApiResponse<Inventory> response = new BaseApiResponse<>(HttpStatus.OK.value(), inventory);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping()
    public ResponseEntity<PagingApiResponse<List<Inventory>>> getAllInventory(@Valid InventoryCriteria inventoryCriteria) {
        PagingApiResponse<List<Inventory>> listPagingApiResponse = inventoryService.getAllInventory(inventoryCriteria);
        return ResponseEntity.ok().body(listPagingApiResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable("id") Long inventoryId) {
        inventoryService.deleteInventory(inventoryId);
    }
}
