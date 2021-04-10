package com.comparison.service.payload.product;

import com.comparison.service.model.Product;
import com.comparison.service.model.ProductDetail;
import com.comparison.service.payload.product.response.ProductResponse;
import com.comparison.service.payload.productdetail.ProductDetailResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class ProductResponseFactory {

    public static ProductResponse create(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getImageUrl(),
                product.getProductDetailList()
                        .stream()
                        .map(productDetail -> new ProductDetailResponse(productDetail.getId(), productDetail.getPrice(), productDetail.getDiscountRate(), productDetail.getProvider().name(), productDetail.getImageUrl()))
                        .collect(Collectors.toList())
        );
    }

    public static ProductResponse create(ProductDetail productDetail) {
        return new ProductResponse(
                productDetail.getProduct().getId(),
                productDetail.getProduct().getName(),
                productDetail.getProduct().getImageUrl(),
                Collections.singletonList(new ProductDetailResponse(productDetail.getId(), productDetail.getPrice(), productDetail.getDiscountRate(), productDetail.getProvider().name(), productDetail.getImageUrl()))
        );
    }
}
