package com.comparison.service.service.impl;

import com.comparison.service.exception.ProductNotFoundException;
import com.comparison.service.model.Product;
import com.comparison.service.repository.ProductRepository;
import com.comparison.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(Integer Id) {
        Optional<Product> productOptional = productRepository.findById(Id);
        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException("Product not found!");
        }
        return productOptional.get();
    }
}
