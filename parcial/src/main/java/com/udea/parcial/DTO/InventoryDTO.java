package com.udea.parcial.dto;

import lombok.Data;
import java.util.List;

@Data
public class InventoryDTO {
    private WarehouseDTO almacen;
    private List<ProductInventoryDTO> productos;
}
