package com.udea.parcial.service;


import com.udea.parcial.dto.InventoryDTO;
import com.udea.parcial.dto.InventoryRequestDTO;
import com.udea.parcial.entity.Inventory;
import com.udea.parcial.entity.Product;
import com.udea.parcial.entity.Warehouse;
import com.udea.parcial.mapper.InventoryMapper;
import com.udea.parcial.repository.InventoryRepository;
import com.udea.parcial.repository.ProductRepository;
import com.udea.parcial.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // Verificar que el almacén existe
        warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado con ID: " + warehouseId));

        // 1. Convertir Long a Integer si tus entidades usan Integer (muy común en parciales)
        Integer idInt = warehouseId.intValue();

        // 2. Buscar primero el Almacén (para la cabecera del JSON)
        Warehouse warehouse = warehouseRepository.findById(Long.valueOf(idInt))
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado con ID: " + warehouseId));

        // 3. Buscar los productos de ese almacén
        List<Inventory> listaInventario = inventoryRepository.findByAlmacenId(idInt);

        // 4. Usar el Mapper inteligente para unir todo
        // (Este es el método toInventoryDTO(Warehouse, List<Inventory>) que creamos antes)
        return inventoryMapper.toInventoryDTO(warehouse, listaInventario);
    }


    // ---------- PUNTO 2: REGISTRAR STOCK EN INVENTARIO ----------
    public InventoryDTO addInventory(InventoryRequestDTO request) {

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado con ID: " + request.getWarehouseId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + request.getProductId()));

        // Verificar si ya existe inventario para este producto en este almacén
        var existingInventory = inventoryRepository.findByAlmacenIdAndProductoId(
                request.getWarehouseId(), request.getProductId());

        Inventory inv;
        if (existingInventory.isPresent()) {
            // Actualizar cantidad existente
            inv = existingInventory.get();
            inv.setCantidad(inv.getCantidad() + request.getCantidad());
        } else {
            // Crear nuevo registro
            inv = new Inventory();
            inv.setAlmacen(warehouse);
            inv.setProducto(product);
            inv.setCantidad(request.getCantidad());
        }

        // Guardar
        Inventory savedInv = inventoryRepository.save(inv);

        // Retornar respuesta
        // TRUCO: Como el DTO de respuesta es complejo, creamos una lista de 1 solo elemento
        // para poder reutilizar el mapper grande.
        return inventoryMapper.toInventoryDTO(warehouse, List.of(savedInv));
    }
}
