package com.comparison.service.controller;

import com.comparison.service.datasource.ProductDataSource;
import com.comparison.service.model.Product;
import com.comparison.service.model.ProductDetail;
import com.comparison.service.model.Provider;
import com.comparison.service.payload.product.ProductResponseFactory;
import com.comparison.service.payload.product.response.ProductResponse;
import com.comparison.service.service.ProductDetailService;
import com.comparison.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductDetailService productDetailService;

    @Autowired
    ProductDataSource productDataSource;

    @GetMapping
    public List<ProductResponse> getAllProduct() {
        return productService.getAllProduct()
                            .stream()
                            .map(product -> ProductResponseFactory.create(product))
                            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable("id") Integer id) {
        Product product = productService.findProductById(id);
        return ProductResponseFactory.create(product);
    }

    @GetMapping("/product-detail/{productId}/{provider}")
    public ProductResponse getProductDetail(@PathVariable("productId") Integer productId, @PathVariable("provider") Provider provider) {
        ProductDetail productDetail = productDetailService.findProductDetailByProviderAndProductId(provider, productId);
        return ProductResponseFactory.create(productDetail);
    }
}
