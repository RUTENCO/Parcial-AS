package com.udea.parcial.dto;

import lombok.Data;

@Data
public class ProductInventoryDTO {
    private Long productoId;
    private String nombreProducto;
    private int cantidad;
}
