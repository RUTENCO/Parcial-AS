package com.udea.parcial.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InventoryRequestDTO {

    private Long warehouseId;   // ID del almacén
    private Long productId;     // ID del producto
    private Integer cantidad;   // Cantidad a agregar
}
