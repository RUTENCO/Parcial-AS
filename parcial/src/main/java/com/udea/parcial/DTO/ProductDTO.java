package com.udea.parcial.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "Información del producto")
public class ProductDTO {
    
    @Schema(description = "ID del producto", example = "1")
    private Long id;
    
    @Schema(description = "Nombre del producto", example = "Producto A")
    private String nombre;
    
    @Schema(description = "Descripción del producto", example = "Descripción detallada")
    private String descripcion;
    
    @Schema(description = "Precio del producto", example = "99.99")
    private BigDecimal precio;
}
