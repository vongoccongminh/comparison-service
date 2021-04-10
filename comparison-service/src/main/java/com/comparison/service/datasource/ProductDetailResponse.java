package com.comparison.service.datasource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetailResponse {
    Integer id;
    String name;
    float price;
    float discountRate;
    String imageUrl;

    @JsonCreator
    public ProductDetailResponse(@JsonProperty("id") Integer id,
                                 @JsonProperty("name") String name,
                                 @JsonProperty("price") float price,
                                 @JsonProperty("discount_rate") float discountRate,
                                 @JsonProperty("image_url") String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountRate = discountRate;
        this.imageUrl = imageUrl;
    }


}
