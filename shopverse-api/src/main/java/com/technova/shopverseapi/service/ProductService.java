package com.technova.shopverseapi.service;

import com.technova.shopverseapi.dto.ProductDTO;
import com.technova.shopverseapi.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAllProducts();
    List<ProductDTO> getAllProductDTOs();
    Optional<Product> getProductById(Long id);
    Optional<ProductDTO> getProductDTOById(Long id); // <- añade este método
    Product createProduct(Product product);
    Product updateProduct(Long id, Product updatedProduct);
    void deleteProduct(Long id);
    ProductDTO toDTO(Product product);
    List<ProductDTO> getByCategoryId(Long categoryId);
}
