package com.udea.parcial.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información del almacén")
public class WarehouseDTO {
    
    @Schema(description = "ID del almacén", example = "1")
    private Long id;
    
    @Schema(description = "Nombre del almacén", example = "Almacén Central")
    private String nombre;
    
    @Schema(description = "Dirección del almacén", example = "Calle 123")
    private String direccion;
    
    @Schema(description = "Ciudad del almacén", example = "Medellín")
    private String ciudad;
}
