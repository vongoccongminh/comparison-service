package com.tiki.service.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Product {
    Integer id;
    String name;
    float price;
    float discountRate;
    String imageUrl;
}
