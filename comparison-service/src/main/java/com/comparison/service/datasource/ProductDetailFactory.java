package com.comparison.service.datasource;

import com.comparison.service.model.ProductDetail;
import com.comparison.service.model.Provider;

public class ProductDetailFactory {

    public static ProductDetail create(ProductDetailResponse productDetailResponse, Provider provider) {
        return ProductDetail.builder()
                .price(productDetailResponse.getPrice())
                .discountRate(productDetailResponse.getDiscountRate())
                .provider(provider)
                .imageUrl(productDetailResponse.getImageUrl())
                .build();
    	
    }
}
