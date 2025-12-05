package com.udea.parcial.controller;

import com.udea.parcial.dto.InventoryDTO;
import com.udea.parcial.dto.InventoryRequest;
import com.udea.parcial.service.InventoryService;
import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    // ---------- PUNTO 1: GET INVENTARIO ----------
    @GetMapping
    public CollectionModel<EntityModel<InventoryDTO>> getInventory(
            @RequestHeader("X-API-VERSION") String version,
            @RequestParam Long warehouseId) {

        List<InventoryDTO> data = service.getInventoryByWarehouse(warehouseId);

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
    public EntityModel<InventoryDTO> addInventory(
            @RequestBody InventoryRequest request,
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
