package com.udea.parcial.controller;

import com.udea.parcial.DTO.InventoryDTO;
import com.udea.parcial.DTO.InventoryRequestDTO;
import com.udea.parcial.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventario", description = "Gestión de existencias por Sede (Warehouse)")
public class InventoryController {

    private final InventoryService service;

    // ---------- PUNTO 1: GET INVENTARIO ----------
    @GetMapping
    @Operation(summary = "Consultar inventario por Sede",
            description = "Retorna la lista de productos y cantidades disponibles en un almacén específico. Incluye enlaces HATEOAS.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario encontrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos o Header faltante"),
            @ApiResponse(responseCode = "404", description = "Sede (Warehouse) no encontrada")
    })
    public CollectionModel<EntityModel<InventoryDTO>> getInventory(
            @Parameter(in = ParameterIn.HEADER, name = "X-API-VERSION", required = true, example = "v1", description = "Versión de la API (v1)")
            @RequestHeader("X-API-VERSION") String version,

            @Parameter(description = "ID de la sede a consultar", example = "1", required = true)
            @RequestParam Long warehouseId) {

        List<InventoryDTO> data = Collections.singletonList(service.getInventoryByWarehouse(warehouseId));

        List<EntityModel<InventoryDTO>> lista = data.stream().map(inv ->
                EntityModel.of(inv,
                        linkTo(methodOn(InventoryController.class)
                                .getInventory(version, warehouseId)).withSelfRel()
                )
        ).toList();

        return CollectionModel.of(lista,
                linkTo(methodOn(InventoryController.class)
                        .getInventory(version, warehouseId)).withSelfRel()
        );
    }

    // ---------- PUNTO 2: POST INVENTARIO ----------
    @PostMapping
    @Operation(summary = "Registrar Inventario",
            description = "Agrega stock a un producto en una sede específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario actualizado/creado"),
            @ApiResponse(responseCode = "409", description = "Conflicto: El producto ya existe en esa sede (si aplica restricción)")
    })
    public EntityModel<InventoryDTO> addInventory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del nuevo inventario", required = true,
                    content = @Content(schema = @Schema(implementation = InventoryRequestDTO.class)))
            @RequestBody InventoryRequestDTO request,

            @Parameter(in = ParameterIn.HEADER, name = "X-API-VERSION", required = true, example = "v1")
            @RequestHeader("X-API-VERSION") String version) {

        InventoryDTO dto = service.addInventory(request);

        return EntityModel.of(dto,
                linkTo(methodOn(InventoryController.class)
                        .addInventory(request, version)).withSelfRel(),
                linkTo(methodOn(InventoryController.class)
                        .getInventory(version, request.getWarehouseId())).withRel("ver-inventario")
        );
    }
}
