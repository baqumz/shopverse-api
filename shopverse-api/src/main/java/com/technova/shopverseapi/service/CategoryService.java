package com.technova.shopverseapi.service;

import com.technova.shopverseapi.dto.CategoryDTO;
import com.technova.shopverseapi.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Long id);
    Category createCategory(Category category);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);

    // Método añadido en Sprint 6
    CategoryDTO getCategoryDTOById(Long id);
}
