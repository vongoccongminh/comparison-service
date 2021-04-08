package com.comparison.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "product_details")
@Data
@Builder
@AllArgsConstructor
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    float price;

    float discountRate;

    @Enumerated(EnumType.STRING)
    Provider provider;

    String imageUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @Override
    public String toString() {
        return "ProductDetail [id=" + id + ", price=" + price + ", product=" + product + "]";
    }
    
   public ProductDetail() {
   }
}
