package com.udea.parcial.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter

@Schema(description = "Datos para registrar inventario")

public class InventoryRequestDTO {

    @Schema(description = "ID del almacén", example = "1")
    private Long warehouseId;   

    @Schema(description = "ID del producto", example = "1")
    private Long productId;     

    @Schema(description = "Cantidad inicial en stock", example = "100")
    private Integer cantidad;   
}
