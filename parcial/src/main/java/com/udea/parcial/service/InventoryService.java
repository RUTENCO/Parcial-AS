package com.udea.parcial.service;

import com.udea.parcial.DTO.InventoryDTO;
import com.udea.parcial.DTO.InventoryRequestDTO; // Asegúrate de tener este DTO o usar el Request del controlador
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
    // CAMBIO: Devuelve un solo objeto DTO (que contiene la lista dentro)
    public InventoryDTO getInventoryByWarehouse(Long warehouseId) {

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

    // ---------- PUNTO 2: REGISTRAR STOCK ----------
    @Transactional
    public InventoryDTO addInventory(InventoryRequestDTO request) {

        // Validar existencia (Casteando IDs a Integer si es necesario)
        Warehouse warehouse = warehouseRepository.findById((long) request.getWarehouseId().intValue())
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));

        Product product = productRepository.findById((long) request.getProductId().intValue())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Crear entidad
        Inventory inv = new Inventory(); // O usar el mapper toEntity si lo tienes simple
        inv.setAlmacen(warehouse);
        inv.setProducto(product);
        inv.setCantidad(request.getCantidad());

        // Guardar
        Inventory savedInv = inventoryRepository.save(inv);

        // Retornar respuesta
        // TRUCO: Como el DTO de respuesta es complejo, creamos una lista de 1 solo elemento
        // para poder reutilizar el mapper grande.
        return inventoryMapper.toInventoryDTO(warehouse, List.of(savedInv));
    }
}
