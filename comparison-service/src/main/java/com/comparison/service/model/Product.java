package com.comparison.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    String imageUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    List<ProductDetail> productDetailList = new ArrayList<>();

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + "]";
    }

    public Product() {
    }
}
