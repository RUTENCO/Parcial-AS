package com.udea.parcial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String ciudad;
}
