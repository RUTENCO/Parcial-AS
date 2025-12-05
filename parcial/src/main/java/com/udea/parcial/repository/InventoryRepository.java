package com.udea.parcial.repository;

import com.udea.parcial.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByAlmacenId(Long warehouseId);
    
    Optional<Inventory> findByAlmacenIdAndProductoId(Long warehouseId, Long productId);

}
