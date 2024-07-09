package site.thanhtungle.inventoryservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import site.thanhtungle.commons.constant.enums.ETourStatus;
import site.thanhtungle.commons.exception.CustomBadRequestException;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.commons.util.CommonPageUtil;
import site.thanhtungle.inventoryservice.mapper.InventoryMapper;
import site.thanhtungle.inventoryservice.model.criteria.InventoryCriteria;
import site.thanhtungle.inventoryservice.model.dto.BookedQuantityRequestDTO;
import site.thanhtungle.inventoryservice.model.dto.InventoryRequestDTO;
import site.thanhtungle.inventoryservice.model.dto.InventoryUpdateRequestDTO;
import site.thanhtungle.inventoryservice.model.dto.TourResponseDTO;
import site.thanhtungle.inventoryservice.model.entity.Inventory;
import site.thanhtungle.inventoryservice.repository.InventoryRepository;
import site.thanhtungle.inventoryservice.service.InventoryService;
import site.thanhtungle.inventoryservice.service.rest.TourApiClient;
import site.thanhtungle.inventoryservice.service.specification.AndFilterSpecification;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final AndFilterSpecification<Inventory> andFilterSpecification;
    private final TourApiClient tourApiClient;
    private final List<String> allowedFilterFieldList = Arrays.stream(Inventory.class.getDeclaredFields())
            .map(Field::getName)
            .toList();

    @Override
    public Inventory createInventory(InventoryRequestDTO inventoryRequestDTO) {
        ResponseEntity<BaseApiResponse<TourResponseDTO>> response = tourApiClient.getTour(inventoryRequestDTO.getTourId());
        if (Objects.isNull(response) || Objects.isNull(response.getBody().getData())) {
            throw new CustomBadRequestException("Cannot create inventory because tour cannot be founded with id: " +
                    inventoryRequestDTO.getTourId());
        }
        TourResponseDTO tourResponseDTO = response.getBody().getData();
        if (tourResponseDTO.getStatus() != ETourStatus.ACTIVE) {
            throw new CustomBadRequestException("Cannot create inventory because tour is inactive.");
        }

        Inventory inventory = inventoryMapper.toEntityInventory(inventoryRequestDTO);
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory updateInventory(Long inventoryId, InventoryUpdateRequestDTO inventoryRequestDTO) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new CustomNotFoundException("No inventory found with that id."));
        inventoryMapper.updateInventory(inventoryRequestDTO, inventory);
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory increaseBookedQuantity(Long inventoryId, BookedQuantityRequestDTO requestDTO) {
        Assert.notNull(inventoryId, "inventoryId cannot be null.");
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new CustomNotFoundException("No inventory found with that id."));

        if (inventory.getAvailableQuantity() <= 0) throw new CustomBadRequestException("There is no availableQuantity.");
        if (requestDTO.getQuantity() > inventory.getAvailableQuantity())
            throw new CustomBadRequestException("The bookedQuantity must less than or equal availableQuantity.");

        Integer bookedQuantity = inventory.getBookedQuantity() + requestDTO.getQuantity();
        if (inventory.getTotalQuantity() < bookedQuantity)
            throw new CustomBadRequestException("The totalQuantity must greater than bookedQuantity.");
        Integer availableQuantity = inventory.getTotalQuantity() - bookedQuantity;
        inventory.setBookedQuantity(bookedQuantity);
        inventory.setAvailableQuantity(availableQuantity);
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory decreaseBookedQuantity(Long inventoryId, BookedQuantityRequestDTO requestDTO) {
        Assert.notNull(inventoryId, "inventoryId cannot be null.");
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new CustomNotFoundException("No inventory found with that id."));

        if (requestDTO.getQuantity() > inventory.getBookedQuantity())
            throw new CustomBadRequestException("The bookedQuantity must greater than or equal to 0.");

        Integer bookedQuantity = inventory.getBookedQuantity() - requestDTO.getQuantity();
        if (inventory.getTotalQuantity() < bookedQuantity)
            throw new CustomBadRequestException("The totalQuantity must greater than bookedQuantity.");
        inventory.setBookedQuantity(bookedQuantity);
        inventory.setAvailableQuantity(inventory.getTotalQuantity() - bookedQuantity);
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory getInventory(Long inventoryId) {
        Assert.notNull(inventoryId, "inventoryId cannot be null.");
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new CustomNotFoundException("No inventory found with that id."));
    }

    @Override
    public PagingApiResponse<List<Inventory>> getAllInventory(InventoryCriteria inventoryCriteria) {
        PageRequest pageRequest = CommonPageUtil
                .getPageRequest(inventoryCriteria.getPage(), inventoryCriteria.getPageSize(), inventoryCriteria.getSort());
        Specification<Inventory> filterBy = andFilterSpecification
                .getAndFilterSpecification(inventoryCriteria.getFilters(), allowedFilterFieldList);
        Page<Inventory> inventoryPage = inventoryRepository.findAll(filterBy, pageRequest);

        List<Inventory> inventoryList = inventoryPage.getContent();
        PageInfo pageInfo = new PageInfo(inventoryCriteria.getPage(), inventoryCriteria.getPageSize(),
                inventoryPage.getTotalElements(), inventoryPage.getTotalPages());
        return new PagingApiResponse<>(HttpStatus.OK.value(), inventoryList, pageInfo);
    }

    @Override
    public void deleteInventory(Long inventoryId) {
        Assert.notNull(inventoryId, "inventoryId cannot be null.");
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new CustomNotFoundException("No inventory found with that id."));
        inventoryRepository.deleteById(inventory.getId());
    }
}
