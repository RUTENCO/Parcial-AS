package com.udea.parcial.DTO;

import lombok.Data;

@Data
public class ProductInventoryDTO {
    private Long productoId;
    private String nombreProducto;
    private int cantidad;
}
