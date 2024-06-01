package site.thanhtungle.inventoryservice.service.impl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.commons.util.CommonPageUtil;
import site.thanhtungle.inventoryservice.mapper.InventoryMapper;
import site.thanhtungle.inventoryservice.model.criteria.InventoryCriteria;
import site.thanhtungle.inventoryservice.model.entity.Inventory;
import site.thanhtungle.inventoryservice.repository.InventoryRepository;
import site.thanhtungle.inventoryservice.service.InventoryService;
import site.thanhtungle.inventoryservice.service.specification.AndFilterSpecification;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;
    private InventoryMapper inventoryMapper;
    private AndFilterSpecification<Inventory> andFilterSpecification;

    @Override
    public Inventory createInventory(@Valid Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory updateInventory(Long inventoryId, @Valid Inventory inventory) {
        Inventory inventoryUpdate = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new CustomNotFoundException("No inventory found with that id."));
        inventoryMapper.updateInventory(inventory, inventoryUpdate);
        return inventoryRepository.save(inventoryUpdate);
    }

    @Override
    public Inventory getInventory(Long inventoryId) {
        if (inventoryId == null) throw new IllegalArgumentException("Inventory id cannot be null.");
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new CustomNotFoundException("No inventory found with that id."));
    }

    @Override
    public PagingApiResponse<List<Inventory>> getAllInventory(InventoryCriteria inventoryCriteria) {
        PageRequest pageRequest = CommonPageUtil
                .getPageRequest(inventoryCriteria.getPage(), inventoryCriteria.getPageSize(), inventoryCriteria.getSort());
        List<String> allowedFieldList = Arrays.stream(Inventory.class.getDeclaredFields()).map(Field::getName).toList();
        Specification<Inventory> filterBy = andFilterSpecification
                .getAndFilterSpecification(inventoryCriteria.getFilters(), allowedFieldList);
        Page<Inventory> inventoryPage = inventoryRepository.findAll(filterBy, pageRequest);

        List<Inventory> inventoryList = inventoryPage.getContent();
        PageInfo pageInfo = new PageInfo(inventoryCriteria.getPage(), inventoryCriteria.getPageSize(),
                inventoryPage.getTotalElements(), inventoryPage.getTotalPages());
        return new PagingApiResponse<>(HttpStatus.OK.value(), inventoryList, pageInfo);
    }

    @Override
    public void deleteInventory(Long inventoryId) {
        if (inventoryId == null) throw new IllegalArgumentException("Inventory id cannot be null.");
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new CustomNotFoundException("No inventory found with that id."));
        inventoryRepository.deleteById(inventory.getId());
    }
}
