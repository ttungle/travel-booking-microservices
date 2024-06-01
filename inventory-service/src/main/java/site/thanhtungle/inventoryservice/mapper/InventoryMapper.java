package site.thanhtungle.inventoryservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import site.thanhtungle.inventoryservice.model.entity.Inventory;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InventoryMapper {

    void updateInventory(Inventory inventory, @MappingTarget Inventory targetInventory);
}
