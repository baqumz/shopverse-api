package com.technova.shopverseapi.service;

import com.technova.shopverseapi.dto.ProductDTO;
import com.technova.shopverseapi.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);

    // Métodos añadidos en Sprint 6
    ProductDTO toDTO(Product product);
    List<ProductDTO> getAllProductDTOs();
    List<ProductDTO> getByCategoryId(Long categoryId);
}
