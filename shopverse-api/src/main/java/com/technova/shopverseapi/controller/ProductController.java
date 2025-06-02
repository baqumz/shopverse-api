package com.technova.shopverseapi.controller;

import com.technova.shopverseapi.dto.ProductDTO;
import com.technova.shopverseapi.model.Product;
import com.technova.shopverseapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(
            summary = "Obtener todos los productos",
            description = "Devuelve una lista de todos los productos en formato DTO."
    )
    @ApiResponse(responseCode = "200", description = "Productos retornados correctamente")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        List<ProductDTO> products = productService.getAllProductDTOs();
        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Obtener producto por ID",
            description = "Devuelve un producto específico por su ID en formato DTO."
    )
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return productService.getProductDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear producto nuevo",
            description = "Crea un nuevo producto y lo devuelve en formato DTO."
    )
    @ApiResponse(responseCode = "201", description = "Producto creado correctamente")
    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody Product product) {
        Product created = productService.createProduct(product);
        ProductDTO dto = productService.toDTO(created);
        return ResponseEntity.status(201).body(dto);
    }

    @Operation(
            summary = "Actualizar producto existente",
            description = "Actualiza un producto existente por su ID."
    )
    @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updated = productService.updateProduct(id, product);
            ProductDTO dto = productService.toDTO(updated);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Eliminar producto por ID",
            description = "Elimina un producto por su ID."
    )
    @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Obtener productos por categoría",
            description = "Devuelve productos filtrados por categoría en formato DTO."
    )
    @ApiResponse(responseCode = "200", description = "Productos por categoría obtenidos correctamente")
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable Long categoryId) {
        List<ProductDTO> products = productService.getByCategoryId(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }
}
