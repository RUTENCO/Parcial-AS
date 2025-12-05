package com.udea.parcial.controller;

import com.udea.parcial.dto.WarehouseDTO;
import com.udea.parcial.entity.Warehouse;
import com.udea.parcial.mapper.WarehouseMapper;
import com.udea.parcial.repository.WarehouseRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/warehouses")
@RequiredArgsConstructor
@Tag(name = "Almacenes", description = "Gestión de almacenes")
public class WarehouseController {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @GetMapping
    @Operation(summary = "Listar todos los almacenes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de almacenes obtenida exitosamente")
    })
    public ResponseEntity<CollectionModel<EntityModel<WarehouseDTO>>> getAllWarehouses(
            @Parameter(in = ParameterIn.HEADER, name = "X-API-VERSION", example = "v1")
            @RequestHeader("X-API-VERSION") String version) {

        List<Warehouse> warehouses = warehouseRepository.findAll();
        
        List<EntityModel<WarehouseDTO>> warehouseList = warehouses.stream()
                .map(warehouse -> EntityModel.of(warehouseMapper.toDTO(warehouse),
                        linkTo(methodOn(WarehouseController.class)
                                .getWarehouseById(warehouse.getId(), version)).withSelfRel()))
                .toList();

        return ResponseEntity.ok(CollectionModel.of(warehouseList,
                linkTo(methodOn(WarehouseController.class)
                        .getAllWarehouses(version)).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener almacén por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Almacén encontrado"),
            @ApiResponse(responseCode = "404", description = "Almacén no encontrado")
    })
    public ResponseEntity<EntityModel<WarehouseDTO>> getWarehouseById(
            @PathVariable Long id,
            @Parameter(in = ParameterIn.HEADER, name = "X-API-VERSION", example = "v1")
            @RequestHeader("X-API-VERSION") String version) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado con ID: " + id));

        return ResponseEntity.ok(EntityModel.of(warehouseMapper.toDTO(warehouse),
                linkTo(methodOn(WarehouseController.class)
                        .getWarehouseById(id, version)).withSelfRel(),
                linkTo(methodOn(WarehouseController.class)
                        .getAllWarehouses(version)).withRel("all-warehouses")));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo almacén")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Almacén creado exitosamente")
    })
    public ResponseEntity<EntityModel<WarehouseDTO>> createWarehouse(
            @RequestBody WarehouseDTO warehouseDTO,
            @Parameter(in = ParameterIn.HEADER, name = "X-API-VERSION", example = "v1")
            @RequestHeader("X-API-VERSION") String version) {

        Warehouse warehouse = new Warehouse();
        warehouse.setNombre(warehouseDTO.getNombre());
        warehouse.setDireccion(warehouseDTO.getDireccion());
        warehouse.setCiudad(warehouseDTO.getCiudad());
        
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        return ResponseEntity.status(201)
                .body(EntityModel.of(warehouseMapper.toDTO(savedWarehouse),
                        linkTo(methodOn(WarehouseController.class)
                                .getWarehouseById(savedWarehouse.getId(), version)).withSelfRel(),
                        linkTo(methodOn(WarehouseController.class)
                                .getAllWarehouses(version)).withRel("all-warehouses")));
    }
}
