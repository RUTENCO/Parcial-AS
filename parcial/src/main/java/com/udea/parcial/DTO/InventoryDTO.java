package com.udea.parcial.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Datos de inventario de un producto en un almacén")
public class InventoryDTO {
    
    @Schema(description = "ID del inventario", example = "1")
    private Long id;
    
    @Schema(description = "Información del almacén")
    private WarehouseDTO almacen;
    
    @Schema(description = "Información del producto")
    private ProductDTO producto;
    
    @Schema(description = "Cantidad disponible", example = "100")
    private Integer cantidad;
}
