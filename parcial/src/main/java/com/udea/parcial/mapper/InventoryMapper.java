package com.udea.parcial.mapper;

import com.udea.parcial.DTO.InventoryDTO;
import com.udea.parcial.DTO.ProductInventoryDTO;
import com.udea.parcial.DTO.WarehouseDTO;
import com.udea.parcial.entity.Inventory;
import com.udea.parcial.entity.Warehouse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InventoryMapper {

    // Convierte la entidad Almacen a su DTO
    public WarehouseDTO toWarehouseDTO(Warehouse warehouse) {
        if (warehouse == null) return null;

        // Usamos el constructor de @AllArgsConstructor que vi en tu archivo WarehouseDTO
        return new WarehouseDTO(
                warehouse.getId().longValue(),
                warehouse.getNombre(),
                warehouse.getDireccion(),
                warehouse.getCiudad()
        );
    }

    // Convierte una línea de inventario a tu ProductInventoryDTO específico
    public ProductInventoryDTO toProductInventoryDTO(Inventory inventory) {
        if (inventory == null) return null;

        ProductInventoryDTO dto = new ProductInventoryDTO();
        // Mapeo exacto a tus campos:
        dto.setProductoId(inventory.getProducto().getId().longValue());
        dto.setNombreProducto(inventory.getProducto().getNombre());
        dto.setCantidad(inventory.getCantidad());

        return dto;
    }

    // Método principal: Ensambla el InventoryDTO completo (Sede + Lista de Productos)
    public InventoryDTO toInventoryDTO(Warehouse warehouse, List<Inventory> inventoryList) {
        InventoryDTO dto = new InventoryDTO();

        // 1. Mapear la cabecera (Sede)
        dto.setAlmacen(toWarehouseDTO(warehouse));

        // 2. Mapear la lista de productos usando el método de arriba
        List<ProductInventoryDTO> productList = inventoryList.stream()
                .map(this::toProductInventoryDTO)
                .collect(Collectors.toList());

        dto.setProductos(productList);

        return dto;
    }
}
