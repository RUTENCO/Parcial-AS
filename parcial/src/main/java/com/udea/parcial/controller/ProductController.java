package com.udea.parcial.controller;

import com.udea.parcial.dto.ProductDTO;
import com.udea.parcial.entity.Product;
import com.udea.parcial.mapper.ProductMapper;
import com.udea.parcial.repository.ProductRepository;
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
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestión de productos")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    @Operation(summary = "Listar todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    })
    public ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> getAllProducts(
            @Parameter(in = ParameterIn.HEADER, name = "X-API-VERSION", example = "v1")
            @RequestHeader("X-API-VERSION") String version) {

        List<Product> products = productRepository.findAll();
        
        List<EntityModel<ProductDTO>> productList = products.stream()
                .map(product -> EntityModel.of(productMapper.toDTO(product),
                        linkTo(methodOn(ProductController.class)
                                .getProductById(product.getId(), version)).withSelfRel()))
                .toList();

        return ResponseEntity.ok(CollectionModel.of(productList,
                linkTo(methodOn(ProductController.class)
                        .getAllProducts(version)).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<EntityModel<ProductDTO>> getProductById(
            @PathVariable Long id,
            @Parameter(in = ParameterIn.HEADER, name = "X-API-VERSION", example = "v1")
            @RequestHeader("X-API-VERSION") String version) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        return ResponseEntity.ok(EntityModel.of(productMapper.toDTO(product),
                linkTo(methodOn(ProductController.class)
                        .getProductById(id, version)).withSelfRel(),
                linkTo(methodOn(ProductController.class)
                        .getAllProducts(version)).withRel("all-products")));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente")
    })
    public ResponseEntity<EntityModel<ProductDTO>> createProduct(
            @RequestBody ProductDTO productDTO,
            @Parameter(in = ParameterIn.HEADER, name = "X-API-VERSION", example = "v1")
            @RequestHeader("X-API-VERSION") String version) {

        Product product = productMapper.toEntity(productDTO);
        product.setId(null); // Para asegurar que se cree uno nuevo
        Product savedProduct = productRepository.save(product);

        return ResponseEntity.status(201)
                .body(EntityModel.of(productMapper.toDTO(savedProduct),
                        linkTo(methodOn(ProductController.class)
                                .getProductById(savedProduct.getId(), version)).withSelfRel(),
                        linkTo(methodOn(ProductController.class)
                                .getAllProducts(version)).withRel("all-products")));
    }
}
