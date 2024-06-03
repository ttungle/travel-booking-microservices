package site.thanhtungle.inventoryservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.inventoryservice.model.criteria.InventoryCriteria;
import site.thanhtungle.inventoryservice.model.dto.InventoryRequestDTO;
import site.thanhtungle.inventoryservice.model.dto.InventoryUpdateRequestDTO;
import site.thanhtungle.inventoryservice.model.entity.Inventory;

import java.util.List;

@Transactional
public interface InventoryService {

    Inventory createInventory(InventoryRequestDTO inventoryRequestDTO);

    Inventory updateInventory(Long inventoryId, InventoryUpdateRequestDTO inventoryUpdateRequestDTO);

    @Transactional(readOnly = true)
    Inventory getInventory(Long inventoryId);

    @Transactional(readOnly = true)
    PagingApiResponse<List<Inventory>> getAllInventory(InventoryCriteria inventoryCriteria);

    void deleteInventory(Long inventoryId);
}
