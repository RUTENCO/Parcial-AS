package com.udea.parcial.service;

import com.udea.parcial.dto.InventoryDTO;
import com.udea.parcial.dto.InventoryRequest;
import com.udea.parcial.entity.Inventory;
import com.udea.parcial.entity.Product;
import com.udea.parcial.entity.Warehouse;
import com.udea.parcial.mapper.InventoryMapper;
import com.udea.parcial.repository.InventoryRepository;
import com.udea.parcial.repository.ProductRepository;
import com.udea.parcial.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final InventoryMapper inventoryMapper;

    // ---------- PUNTO 1: CONSULTAR INVENTARIO POR ALMACÉN ----------
    public List<InventoryDTO> getInventoryByWarehouse(Long warehouseId) {

        List<Inventory> lista = inventoryRepository.findByAlmacenId(warehouseId);

        return lista.stream()
                .map(inventoryMapper::toDTO)
                .toList();
    }

    // ---------- PUNTO 2: REGISTRAR STOCK EN INVENTARIO ----------
    public InventoryDTO addInventory(InventoryRequest request) {

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Inventory inv = inventoryMapper.toEntity(
                warehouse,
                product,
                request.getCantidad()
        );

        inventoryRepository.save(inv);

        return inventoryMapper.toDTO(inv);
    }
}
