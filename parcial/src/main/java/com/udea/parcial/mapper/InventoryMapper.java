package com.udea.parcial.mapper;

import com.udea.parcial.dto.InventoryDTO;
import com.udea.parcial.dto.WarehouseDTO;
import com.udea.parcial.entity.Inventory;
import com.udea.parcial.entity.Product;
import com.udea.parcial.entity.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public Inventory toEntity(Warehouse warehouse, Product product, Integer cantidad) {
        Inventory inv = new Inventory();
        inv.setAlmacen(warehouse);
        inv.setProducto(product);
        inv.setCantidad(cantidad);
        return inv;
    }

    public InventoryDTO toDTO(Inventory inv) {
        InventoryDTO dto = new InventoryDTO();
        dto.setWarehouseId(inv.getAlmacen().getId());
        dto.setProductName(inv.getProducto().getNombre());
        dto.setStock(inv.getCantidad());
        return dto;
    }

}
