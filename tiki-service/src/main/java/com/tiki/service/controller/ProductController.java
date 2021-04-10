package com.tiki.service.controller;

import com.tiki.service.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    List<Product> productList = Arrays.asList(
            new Product(1, "Iphone 12", 998.4f, 0.3f,"https://fptshop.com.vn/dien-thoai/iphone-12-tiki.jpg"),
            new Product(2, "Iphone 12 Pro", 1998.4f, 1.3f,"https://fptshop.com.vn/dien-thoai/iphone-12-pro-tiki.jpg"),
            new Product(3, "Iphone 12 Pro Max", 3998.4f, 2.3f,"https://fptshop.com.vn/dien-thoai/iphone-12-pro-max-tiki.jpg"));

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Integer id) {
        return productList.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found!"));
    }
}
