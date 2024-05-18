package site.thanhtungle.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.thanhtungle.inventoryservice.model.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
