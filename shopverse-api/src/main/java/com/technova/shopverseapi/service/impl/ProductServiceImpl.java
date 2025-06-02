package com.technova.shopverseapi.service.impl;

import com.technova.shopverseapi.dto.ProductDTO;
import com.technova.shopverseapi.model.Product;
import com.technova.shopverseapi.repository.ProductRepository;
import com.technova.shopverseapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductDTO> getAllProductDTOs() {
        return productRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<ProductDTO> getProductDTOById(Long id) {
        return productRepository.findById(id).map(this::toDTO); // Implementación aquí
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con id " + id));

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Producto no encontrado con id " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO toDTO(Product product) {
        String categoryName = product.getCategory() != null ? product.getCategory().getName() : null;
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                categoryName
        );
    }

    @Override
    public List<ProductDTO> getByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::toDTO)
                .toList();
    }
}
