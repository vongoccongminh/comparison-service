package com.comparison.service.payload.product.response;

import com.comparison.service.payload.productdetail.ProductDetailResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

import java.util.List;

@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductResponse {
    Integer id;
    String name;
    String imageUrl;
    List<ProductDetailResponse> productDetailList;
}
