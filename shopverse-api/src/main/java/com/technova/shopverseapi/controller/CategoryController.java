package com.technova.shopverseapi.controller;

import com.technova.shopverseapi.dto.CategoryDTO;
import com.technova.shopverseapi.model.Category;
import com.technova.shopverseapi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categorías", description = "Operaciones relacionadas con categorías")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(
            summary = "Obtener todas las categorías",
            description = "Devuelve una lista con todas las categorías disponibles."
    )
    @ApiResponse(responseCode = "200", description = "Lista de categorías retornada correctamente")
    @ApiResponse(responseCode = "204", description = "No hay categorías disponibles")
    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryService.getAllCategories();
        return categories.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(categories);
    }

    @Operation(
            summary = "Obtener categoría por ID",
            description = "Busca y devuelve una categoría específica según su identificador."
    )
    @ApiResponse(responseCode = "200", description = "Categoría encontrada correctamente")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear una nueva categoría",
            description = "Registra una nueva categoría, accesible solo por administradores."
    )
    @ApiResponse(responseCode = "201", description = "Categoría creada correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos de categoría")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        Category created = categoryService.createCategory(category);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(
            summary = "Actualizar categoría existente",
            description = "Actualiza los datos de una categoría ya existente, accesible solo por administradores."
    )
    @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @Valid @RequestBody Category category) {
        try {
            Category updated = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Eliminar categoría por ID",
            description = "Elimina una categoría según su identificador, accesible solo por administradores."
    )
    @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Detalles de categoría con productos",
            description = "Obtiene información detallada de una categoría incluyendo los nombres de sus productos."
    )
    @ApiResponse(responseCode = "200", description = "Detalles obtenidos correctamente")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    @GetMapping("/{id}/details")
    public ResponseEntity<CategoryDTO> getCategoryDetails(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService.getCategoryDTOById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
