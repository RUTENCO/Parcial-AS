package com.udea.parcial.DTO;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
}
