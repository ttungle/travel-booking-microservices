package site.thanhtungle.inventoryservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import site.thanhtungle.inventoryservice.model.dto.InventoryRequestDTO;
import site.thanhtungle.inventoryservice.model.dto.InventoryUpdateRequestDTO;
import site.thanhtungle.inventoryservice.model.entity.Inventory;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Slf4j
public abstract class InventoryMapper {

    @Mapping(target = "availableQuantity", source = "inventoryRequestDTO", qualifiedByName = "calculateAvailableQuantity")
    public abstract Inventory toEntityInventory(InventoryRequestDTO inventoryRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "availableQuantity", expression = "java(calculateAvailableQuantityUpdate(inventoryUpdateRequestDTO, targetInventory))")
    public abstract void updateInventory(InventoryUpdateRequestDTO inventoryUpdateRequestDTO, @MappingTarget Inventory targetInventory);

    @Named("calculateAvailableQuantity")
    protected Integer calculateAvailableQuantity(InventoryRequestDTO inventoryRequestDTO) {
        if (inventoryRequestDTO == null) return null;
        return inventoryRequestDTO.getTotalQuantity() - inventoryRequestDTO.getBookedQuantity();
    }

    protected Integer calculateAvailableQuantityUpdate(InventoryUpdateRequestDTO inventoryUpdateRequestDTO, Inventory targetInventory) {
        if (inventoryUpdateRequestDTO == null) return targetInventory.getAvailableQuantity();

        if (inventoryUpdateRequestDTO.getTotalQuantity() != null && inventoryUpdateRequestDTO.getBookedQuantity() != null) {
            if (inventoryUpdateRequestDTO.getTotalQuantity() < inventoryUpdateRequestDTO.getBookedQuantity()) {
                throw new IllegalArgumentException("totalQuantity cannot be less than bookedQuantity.");
            }
            return inventoryUpdateRequestDTO.getTotalQuantity() - inventoryUpdateRequestDTO.getBookedQuantity();
        }

        if (inventoryUpdateRequestDTO.getBookedQuantity() != null) {
            if (inventoryUpdateRequestDTO.getBookedQuantity() > targetInventory.getTotalQuantity()) {
                throw new IllegalArgumentException("bookedQuantity cannot greater than totalQuantity.");
            }
            return targetInventory.getTotalQuantity() - inventoryUpdateRequestDTO.getBookedQuantity();
        }

        if (inventoryUpdateRequestDTO.getTotalQuantity() != null) {
            if (inventoryUpdateRequestDTO.getTotalQuantity() < targetInventory.getBookedQuantity()) {
                throw new IllegalArgumentException("totalQuantity cannot be less than bookedQuantity.");
            }
            return inventoryUpdateRequestDTO.getTotalQuantity() - targetInventory.getBookedQuantity();
        }

        return targetInventory.getAvailableQuantity();
    }
}
