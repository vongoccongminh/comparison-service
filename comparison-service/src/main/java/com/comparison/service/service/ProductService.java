package com.comparison.service.service;

import com.comparison.service.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProduct();
    Product findProductById(Integer Id);
}
