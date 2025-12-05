package com.udea.parcial.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InventoryRequest {

    private Long warehouseId;   // ID del almacén
    private Long productId;     // ID del producto
    private Integer cantidad;   // Cantidad a agregar
}
