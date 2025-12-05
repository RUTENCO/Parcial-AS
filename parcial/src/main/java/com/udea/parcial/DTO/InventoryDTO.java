package com.udea.parcial.DTO;

import lombok.Data;
import java.util.List;

@Data
public class InventoryDTO {
    private com.udea.parcial.DTO.WarehouseDTO almacen;
    private List<com.udea.parcial.DTO.ProductInventoryDTO> productos;
}
