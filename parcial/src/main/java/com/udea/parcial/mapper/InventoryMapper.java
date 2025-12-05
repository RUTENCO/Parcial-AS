package com.udea.parcial.mapper;


import com.udea.parcial.dto.InventoryDTO;
import com.udea.parcial.dto.WarehouseDTO;
import com.udea.parcial.dto.ProductDTO;

import com.udea.parcial.entity.Inventory;
import com.udea.parcial.entity.Warehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InventoryMapper {


    private final WarehouseMapper warehouseMapper;
    private final ProductMapper productMapper;

    public InventoryDTO toDTO(Inventory inv) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(inv.getId());
        dto.setAlmacen(warehouseMapper.toDTO(inv.getAlmacen()));
        dto.setProducto(productMapper.toDTO(inv.getProducto()));
        dto.setCantidad(inv.getCantidad());
        return dto;
    }

}
