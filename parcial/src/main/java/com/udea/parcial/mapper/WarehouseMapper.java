package com.udea.parcial.mapper;

import com.udea.parcial.DTO.WarehouseDTO;
import com.udea.parcial.entity.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {

    public WarehouseDTO toDTO(Warehouse w) {
        WarehouseDTO dto = new WarehouseDTO();
        dto.setId(w.getId());
        dto.setNombre(w.getNombre());
        dto.setDireccion(w.getDireccion());
        dto.setCiudad(w.getCiudad());
        return dto;
    }
}
