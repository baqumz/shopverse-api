package com.technova.shopverseapi.controller;

import com.technova.shopverseapi.model.Category;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private List<Category> categories = new ArrayList<>();

    public CategoryController() {
        categories.add(new Category(1L, "Tecnología", "Productos electrónicos y computación"));
        categories.add(new Category(2L, "Hogar", "Artículos para el hogar y decoración"));
        categories.add(new Category(3L, "Indumentaria", "Ropa y accesorios"));
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categories;
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categories.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        categories.add(category);
        return category;
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        for (Category c : categories) {
            if (c.getId().equals(id)) {
                c.setName(updatedCategory.getName());
                c.setDescription(updatedCategory.getDescription());
                return c;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        boolean removed = categories.removeIf(c -> c.getId().equals(id));
        return removed ? "Categoría eliminada con éxito." : "Categoría no encontrada.";
    }
}